package com.wisdomcommand.communication.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import java.security.KeyPair;
import com.wisdomcommand.communication.service.impl.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import javax.sql.DataSource;
import java.util.*;

/**
 * 授权/认证服务器配置
 * @Auther ChenShuHong
 * @Date 2020-06-03 11:33
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
  @Autowired
  private UserDetailServiceImpl userDetailService;

  // 认证管理器
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private DataSource dataSource;

  /**
   * access_token存储器
   * 这里存储在数据库，大家可以结合自己的业务场景考虑将access_token存入数据库还是redis
   *get （网页受权）
   * http://192.168.2.17:8083/auth-consumer/oauth/authorize?response_type=code&client_id=app&redirect_uri=https://www.baidu.com
   * post （受权码）
   * http://192.168.2.17:8083/auth-consumer/oauth/token?grant_type=authorization_code&code=POE5z8&redirect_uri=https://www.baidu.com&scope=all&client_id=app&client_secret=app
   * post （密码受权模式）
   * http://192.168.2.17:8000/auth-consumer/oauth/token?grant_type=password&client_id=app&client_secret=app&username=app&password=111111
   * post (刷新token,这个值refresh_token在获取token接口中的获取)
   * http://192.168.2.17:8083/auth-consumer/oauth/token?grant_type=refresh_token&client_id=app&client_secret=app&refresh_token=a3827afa-f17c-40ea-b6be-739380b8f603
   * post (验证token是否有效)
   * http://192.168.2.17:8083/auth-consumer/oauth/check_token?token=85f2f91e-2b5b-47e8-b6a7-ecf00c710b6a
   */


//使用jwt  (jwt)
  public TokenStore tokenStore() {
    return new JwtTokenStore(jwtAccessTokenConverter());
  }


  @Bean
  protected JwtAccessTokenConverter jwtAccessTokenConverter() {
    //converter.setSigningKey(JwtConstant.SigningKey);
    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    converter.setKeyPair(keyPair());
    return converter;
  }

  @Bean
  public KeyPair keyPair() {
    KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("ffzs-jwt.jks"), "ffzs00".toCharArray());
    return keyStoreKeyFactory.getKeyPair("ffzs-jwt");

  }

  @Bean
  public CustomTokenEnhancer customTokenEnhancer() {
    return new CustomTokenEnhancer();
  }

  /**
   * 认证服务器Endpoints配置 （jwt）
   * @param endpoints
   * @throws Exception
   */
  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    //如果需要使用refresh_token模式则需要注入userDetailService
    TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
    enhancerChain.setTokenEnhancers(Arrays.asList(customTokenEnhancer(), jwtAccessTokenConverter()));
    endpoints.authenticationManager(this.authenticationManager)
        .userDetailsService(userDetailService)
        .tokenStore(tokenStore())
        .accessTokenConverter(jwtAccessTokenConverter())
       // 注入自定义的tokenservice，如果不使用自定义的tokenService那么就需要将tokenServce里的配置移到这里
        .tokenServices(tokenServices())
        .tokenEnhancer(enhancerChain);
  }

  @Primary
  @Bean
  public DefaultTokenServices tokenServices(){
    TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
    enhancerChain.setTokenEnhancers(Arrays.asList(customTokenEnhancer(), jwtAccessTokenConverter()));
    DefaultTokenServices tokenServices = new DefaultTokenServices();
    tokenServices.setTokenEnhancer(jwtAccessTokenConverter());
    tokenServices.setTokenStore(tokenStore());
    tokenServices.setTokenEnhancer(enhancerChain);
    tokenServices.setSupportRefreshToken(true);
    //设置token有效期，默认12小时，此处修改为6小时   21600
    tokenServices.setAccessTokenValiditySeconds(60 * 60 * 6);
    //设置refresh_token的有效期，默认30天，此处修改为7天
    tokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 7);
    return tokenServices;
  }



  /**
   * 从数据库读取clientDetails相关配置
   * 有InMemoryClientDetailsService 和 JdbcClientDetailsService 两种方式选择
   */
  @Bean
  public ClientDetailsService clientDetails() {
    return new JdbcClientDetailsService(dataSource);
  }

  /**
   * 注入密码加密实现器
   */
  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }



  /**
   * 认证服务器相关接口权限管理
   */
  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    security.tokenKeyAccess("permitAll()")
        //访问token校验/oauth/check_token（isAuthenticated()点登陆才允许访问，permitAll() 直接访问不需要登陆）
        .checkTokenAccess("permitAll()")
        .allowFormAuthenticationForClients();
  }

  /**
   * client存储方式，此处使用jdbc存储
   * 配置appid appkey  回调地址  token有效期
   */
  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.withClientDetails(clientDetails());
  }
}
