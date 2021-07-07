package com.wisdomcommand.gateway.authorization;


import cn.hutool.core.convert.Convert;
import com.wisdomcommand.gateway.config.IgnoreUrlsConfig;
import com.wisdomcommand.gateway.constant.AuthConstant;
import com.wisdomcommand.gateway.constant.RedisConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 鉴权管理器，用于判断是否有资源的访问权限
 * Created by macro on 2
 */
@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private static Logger LOGGER = LoggerFactory.getLogger(AuthorizationManager.class);

    @Autowired
    IgnoreUrlsConfig ignoreUrlsConfig;

    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;


    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {


        //请求资源
        String requestPath = authorizationContext.getExchange().getRequest().getURI().getPath();
        // 是否直接放行
        if (permitAll(requestPath)) {
            return Mono.just(new AuthorizationDecision(true));
        }
        LOGGER.info("请求地址：{}",requestPath);
        //从Redis中获取当前路径可访问角色列表
       // Object obj = redisTemplate.opsForHash().get(RedisConstant.RESOURCE_ROLES_MAP, requestPath);
        Object obj=null;
        Set<Object> keys = redisTemplate.opsForHash().keys(RedisConstant.RESOURCE_ROLES_MAP);
        for(Object x :keys){
            if( requestPath.contains(x.toString())){
                obj = redisTemplate.opsForHash().get(RedisConstant.RESOURCE_ROLES_MAP, x.toString());
               continue;
            }
        }




        List<String> authorities = Convert.toList(String.class,obj);
        authorities = authorities.stream().map(i -> i = AuthConstant.AUTHORITY_PREFIX + i).collect(Collectors.toList());
        //认证通过且角色匹配的用户可访问当前路径
        return mono
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(authorities::contains)
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

    /**
     * 校验是否属于静态资源
     * @param requestPath 请求路径
     * @return
     */
    private boolean permitAll(String requestPath) {
        return ignoreUrlsConfig.getPermitAll().stream()
            .filter(r -> antPathMatcher.match(r, requestPath)).findFirst().isPresent();
    }
}
