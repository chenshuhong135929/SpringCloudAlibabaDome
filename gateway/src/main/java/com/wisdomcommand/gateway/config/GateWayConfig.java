package com.wisdomcommand.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 网关配置
 */
@Configuration
public class GateWayConfig
{
  @Bean
  public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder)
  {
    RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();

    return routes.build();
  }
}
