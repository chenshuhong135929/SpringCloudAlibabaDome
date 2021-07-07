package com.wisdomcommand.communication.controller;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

/**
 * @Auther ChenShuHong
 * @Date 2021-07-02 16:46
 * 获取公钥的接口
 */
@RestController
public class KeyPairController {

  @Autowired
  private KeyPair keyPair;

  @GetMapping("/rsa/publicKey")
  public Map<String, Object> getKey() {
    RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
    RSAKey key = new RSAKey.Builder(publicKey).build();
    return new JWKSet(key).toJSONObject();
  }

}
