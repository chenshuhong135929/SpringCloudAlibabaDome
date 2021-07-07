package com.wisdomcommand.gateway.config;

import cn.hutool.core.collection.ConcurrentHashSet;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * 网关白名单配置
 * Created by macro on 2020/6/17.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Component
@ConfigurationProperties(prefix="secure.ignore")
public class IgnoreUrlsConfig {
  private Set<String> permitAll = new ConcurrentHashSet<>();
    IgnoreUrlsConfig(){
      permitAll.add("/");
      permitAll.add("/error");
      permitAll.add("/favicon.ico");
      permitAll.add("/**/v2/api-docs/**");
      permitAll.add("/**/swagger-resources/**");
      permitAll.add("/webjars/**");
      permitAll.add("/doc.html");
      permitAll.add("/swagger-ui.html");
      permitAll.add("/**/oauth/**");
      permitAll.add("/**/current/get");
      permitAll.add("/**/login");

    }
}
