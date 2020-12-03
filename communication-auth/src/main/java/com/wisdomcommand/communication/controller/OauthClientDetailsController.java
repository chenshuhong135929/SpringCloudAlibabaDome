package com.wisdomcommand.communication.controller;

import com.communication.common.CommonResult;
import com.wisdomcommand.communication.entity.OauthClientDetails;
import com.wisdomcommand.communication.entity.User;
import com.wisdomcommand.communication.service.OauthClientDetailsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther ChenShuHong
 * @Date 2020-11-27 15:35
 */
@RestController
@RequestMapping("oauthClientDetails")
@Api(tags = "授权客户端资源模块")
public class OauthClientDetailsController {


  @Autowired
  OauthClientDetailsService oauthClientDetailsService;


  @PostMapping("addOauthClientDetails")
  @ApiOperation(value = "添加授权用户")
  public CommonResult addOauthClientDetails(@RequestBody OauthClientDetails oauthClientDetails) throws  Exception{
    return oauthClientDetailsService.addOauthClientDetails(oauthClientDetails).get();
  }



  @PostMapping("deleteOauthClientDetails")
  @ApiOperation(value = "删除授权用户")
  public CommonResult deleteOauthClientDetails(@RequestBody List<String> ids) throws  Exception{
    return oauthClientDetailsService.deleteOauthClientDetails(ids).get();
  }


}
