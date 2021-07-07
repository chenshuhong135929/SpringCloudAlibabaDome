package com.wisdomcommand.gateway.config;

import cn.hutool.core.util.ArrayUtil;
import com.wisdomcommand.gateway.authorization.AuthorizationManager;
import com.wisdomcommand.gateway.component.RestAuthenticationEntryPoint;
import com.wisdomcommand.gateway.component.RestfulAccessDeniedHandler;
import com.wisdomcommand.gateway.constant.AuthConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.server.resource.web.server.ServerBearerTokenAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import javax.sql.DataSource;

/**
 * @Auther ChenShuHong
 * @Date 2020-06-09 14:27
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
  private static final String MAX_AGE = "18000L";



  @Autowired
   AuthorizationManager authorizationManager;
  @Autowired
 IgnoreUrlsConfig ignoreUrlsConfig;
  @Autowired
 RestfulAccessDeniedHandler restfulAccessDeniedHandler;
  @Autowired
 RestAuthenticationEntryPoint restAuthenticationEntryPoint;

  public SecurityConfig() {
  }

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    http.oauth2ResourceServer().jwt()
        .jwtAuthenticationConverter(jwtAuthenticationConverter());
         http.authorizeExchange()
        .pathMatchers(ArrayUtil.toArray(ignoreUrlsConfig.getPermitAll(),String.class)).permitAll()//白名单配置
        .anyExchange().access(authorizationManager)//鉴权管理器配置
        .and().exceptionHandling()
        .accessDeniedHandler(restfulAccessDeniedHandler)//处理未授权
        .authenticationEntryPoint(restAuthenticationEntryPoint)//处理未认证
        .and()
        .addFilterAt(corsFilter(), SecurityWebFiltersOrder.CORS)
        .csrf().disable();
    return http.build();
  }

  @Bean
  public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
    JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    jwtGrantedAuthoritiesConverter.setAuthorityPrefix(AuthConstant.AUTHORITY_PREFIX);
    jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(AuthConstant.AUTHORITY_CLAIM_NAME);
    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
    return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
  }




  /**
   * 跨域配置
   */
  public WebFilter corsFilter() {
    return (ServerWebExchange ctx, WebFilterChain chain) -> {
      ServerHttpRequest request = ctx.getRequest();
      if (CorsUtils.isCorsRequest(request)) {
        HttpHeaders requestHeaders = request.getHeaders();
        ServerHttpResponse response = ctx.getResponse();
        HttpMethod requestMethod = requestHeaders.getAccessControlRequestMethod();
        HttpHeaders headers = response.getHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, requestHeaders.getOrigin());
        headers.addAll(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, requestHeaders.getAccessControlRequestHeaders());
        if (requestMethod != null) {
          headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, requestMethod.name());
        }
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");
        headers.add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, MAX_AGE);
        if (request.getMethod() == HttpMethod.OPTIONS) {
          response.setStatusCode(HttpStatus.OK);
          return Mono.empty();
        }
      }
      return chain.filter(ctx);
    };
  }
}
