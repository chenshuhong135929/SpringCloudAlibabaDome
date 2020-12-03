package com.wisdomcommand.communication.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.communication.common.CommonResult;
import com.wisdomcommand.communication.entity.OauthClientDetails;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface OauthClientDetailsService extends IService<OauthClientDetails> {



  CompletableFuture<CommonResult>   addOauthClientDetails(OauthClientDetails oauthClientDetails );

  CompletableFuture<CommonResult>  deleteOauthClientDetails(List<String> ids);

}
