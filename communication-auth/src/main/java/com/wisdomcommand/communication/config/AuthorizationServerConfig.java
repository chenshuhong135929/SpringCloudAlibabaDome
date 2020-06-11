package com.wisdomcommand.communication.config;

import com.wisdomcommand.communication.service.impl.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;

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
  @Autowired
  private RedisConnectionFactory redisConnectionFactory;
  // 认证管理器
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private DataSource dataSource;

  /**
   * access_token存储器
   * 这里存储在数据库，大家可以结合自己的业务场景考虑将access_token存入数据库还是redis
   *get （网页受权）
   * http://192.168.2.17:8083/oauth/authorize?response_type=code&client_id=app&redirect_uri=https://www.baidu.com
   * post （受权码）
   * http://192.168.2.17:8083/oauth/token?grant_type=authorization_code&code=POE5z8&redirect_uri=https://www.baidu.com&scope=all&client_id=app&client_secret=app
   * post （密码受权模式）
   * http://192.168.2.17:8083/oauth/token?grant_type=password&redirect_uri=https://www.baidu.com&scope=all&client_id=app&client_secret=app&username=app&password=111111
   * post (刷新token,这个值refresh_token在获取token接口中的获取)
   * http://192.168.2.17:8083/oauth/token?grant_type=refresh_token&client_id=app&client_secret=app&refresh_token=a3827afa-f17c-40ea-b6be-739380b8f603
   * post (验证token是否有效)
   * http://192.168.2.17:8083/oauth/check_token?token=85f2f91e-2b5b-47e8-b6a7-ecf00c710b6a
   */

  @Bean
  public TokenStore tokenStore() {
    return new JdbcTokenStore(dataSource);
  }
 /* @Bean
  public TokenStore tokenStore() {
    return new RedisTokenStore(redisConnectionFactory);
  }*/
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
   * 认证服务器Endpoints配置
   */
  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    //如果需要使用refresh_token模式则需要注入userDetailService
    endpoints.userDetailsService(userDetailService);
    endpoints.authenticationManager(this.authenticationManager);
    endpoints.tokenStore(tokenStore());
  }

  /**
   * 认证服务器相关接口权限管理
   */
  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    security.tokenKeyAccess("permitAll()")
        .checkTokenAccess("isAuthenticated()")
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
