package com.wisdomcommand.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;

/**
 * 拦截校验
 */
@Component
@Slf4j
public class MyLogGateWayFilter implements GlobalFilter,Ordered{

    // url匹配器
    private AntPathMatcher pathMatcher = new AntPathMatcher();


    @Override
    public int getOrder() {
        // TODO Auto-generated method stub
        return -500;
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("url **RequestPath*********:  "+exchange.getRequest().getPath());
        log.info("***********come in MyLogGateWayFilter:  "+new Date());

        String userName = exchange.getRequest().getQueryParams().getFirst("userName");

//        if(userName == null)
//        {
//            log.info("*******用户名为null，非法用户，o(╥﹏╥)o");
//            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
//            return exchange.getResponse().setComplete();
//        }

        return chain.filter(exchange);

    }




}
