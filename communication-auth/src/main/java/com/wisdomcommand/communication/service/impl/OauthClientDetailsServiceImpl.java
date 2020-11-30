package com.wisdomcommand.communication.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.communication.common.CommonResult;
import com.wisdomcommand.communication.entity.OauthClientDetails;
import com.wisdomcommand.communication.mapper.OauthClientDetailsMapper;
import com.wisdomcommand.communication.service.OauthClientDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class OauthClientDetailsServiceImpl extends ServiceImpl<OauthClientDetailsMapper, OauthClientDetails> implements OauthClientDetailsService{


  @Autowired
  OauthClientDetailsMapper oauthClientDetailsMapper;
  @Autowired
  PasswordEncoder passwordEncoder;
  @Override
  public CompletableFuture<CommonResult> addOauthClientDetails(OauthClientDetails oauthClientDetails) {

    CommonResult result = new CommonResult();
    CompletableFuture<CommonResult> future = CompletableFuture.supplyAsync(()->{

      if(StrUtil.isEmpty(oauthClientDetails.getClientId())){
        result.setCode(HttpStatus.HTTP_INTERNAL_ERROR);
        result.setMessage("客户端Id不能为空");
        return result;
      }

      if(oauthClientDetailsMapper.selectByClientId(oauthClientDetails.getClientId())>0){
        result.setCode(HttpStatus.HTTP_INTERNAL_ERROR);
        result.setMessage("该用户资源已经添加");
        return result;
      }
      if(StrUtil.isEmpty(oauthClientDetails.getResourceIds())){
        result.setCode(HttpStatus.HTTP_INTERNAL_ERROR);
        result.setMessage("资源Id不能为空");
        return result;
      }


      if(StrUtil.isEmpty(oauthClientDetails.getClientSecret())){
        result.setCode(HttpStatus.HTTP_INTERNAL_ERROR);
        result.setMessage("客户密钥不能为空");
        return result;
      }


      if(StrUtil.isEmpty(oauthClientDetails.getScope())){
        oauthClientDetails.setScope("all");

      }


      if(StrUtil.isEmpty(oauthClientDetails.getAuthorizedGrantTypes())){
        result.setCode(HttpStatus.HTTP_INTERNAL_ERROR);
        result.setMessage("授权类型不能为空");
        return result;
      }
      oauthClientDetails.setClientSecret(passwordEncoder.encode(oauthClientDetails.getClientSecret()));
      oauthClientDetailsMapper.addOauthClientDetails(oauthClientDetails);
      result.setCode(HttpStatus.HTTP_OK);
      result.setMessage("添加授权客户端资源成功");
      return result;
    });


    future.exceptionally((e) -> {
      e.printStackTrace();
      result.setCode(HttpStatus.HTTP_INTERNAL_ERROR);
      log.error("添加授权客户端资源["+e.getMessage() +"]");
      result.setMessage("系统出错误，请联系管理员。");
      return result;
    });


    return future;
  }
}
