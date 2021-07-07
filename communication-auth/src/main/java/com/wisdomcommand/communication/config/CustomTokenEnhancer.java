package com.wisdomcommand.communication.config;


import com.wisdomcommand.communication.entity.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 *  Jwt内容增强器
 * @Auther ChenShuHong
 * @Date 2021-06-30 15:22
 */
@Component
public class CustomTokenEnhancer implements TokenEnhancer {
  @Override
  public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
    org.springframework.security.core.userdetails.User securityUser = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
    Map<String, Object> info = new HashMap<>();
    //把用户ID设置到JWT中
    info.put("id", securityUser.getUsername());
    ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
    return accessToken;

  }
}
