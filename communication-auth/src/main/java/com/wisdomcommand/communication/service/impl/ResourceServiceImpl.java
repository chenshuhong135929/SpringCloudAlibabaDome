package com.wisdomcommand.communication.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.wisdomcommand.communication.constant.RedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * 资源与角色匹配关系管理业务类
 * Created by macro on
 */
@Service
public class ResourceServiceImpl {

    private Map<String, List<String>> resourceRolesMap;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @PostConstruct
    public void initData() {
        redisTemplate.opsForHash().keys(RedisConstant.RESOURCE_ROLES_MAP).stream().forEach(x-> redisTemplate.opsForHash().delete( RedisConstant.RESOURCE_ROLES_MAP,x));
        resourceRolesMap = new TreeMap<>();
        resourceRolesMap.put("/basics-consumer/paymentSQL", CollUtil.toList("ADMIN"));
        resourceRolesMap.put("/basics-consumer/currentUser", CollUtil.toList("ADMIN"));
        redisTemplate.opsForHash().putAll(RedisConstant.RESOURCE_ROLES_MAP, resourceRolesMap);
    }
}
