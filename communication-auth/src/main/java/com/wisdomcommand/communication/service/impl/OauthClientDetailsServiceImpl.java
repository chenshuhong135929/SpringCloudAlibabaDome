package com.wisdomcommand.communication.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.communication.common.CommonResult;
import com.wisdomcommand.communication.entity.OauthClientDetails;
import com.wisdomcommand.communication.mapper.OauthClientDetailsMapper;
import com.wisdomcommand.communication.service.OauthClientDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class OauthClientDetailsServiceImpl extends ServiceImpl<OauthClientDetailsMapper, OauthClientDetails> implements OauthClientDetailsService{


  @Autowired
  OauthClientDetailsMapper oauthClientDetailsMapper;

  @Override
  public CompletableFuture<CommonResult> addOauthClientDetails(OauthClientDetails oauthClientDetails) {

    return null;
  }
}
