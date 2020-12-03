package com.wisdomcommand.communication.controller;

import com.communication.common.CommonResult;
import com.wisdomcommand.communication.entity.User;
import com.wisdomcommand.communication.mapper.UserMapper;
import com.wisdomcommand.communication.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * @Auther ChenShuHong
 * @Date 2020-06-03 11:37
 */
@RestController
@RequestMapping("user")
@Api(tags = "授权用户模块")
public class UserController {
  @Autowired
  public UserService userService;


  /**
   * 获取授权的用户信息
   * @param principal 当前用户
   * @return 授权信息
   */
  @GetMapping("current")
  @ApiOperation(value = "获取授权的用户信息")
  public Principal user(Principal principal){

    return principal;
  }

  @PostMapping("addUser")
  @ApiOperation(value = "添加授权用户")
  public CommonResult addUser(@RequestBody User user) throws  Exception{
    return userService.addUser(user).get();
  }

  @PostMapping("deleteUser")
  @ApiOperation(value = "删除授权用户")
  public CommonResult deleteUser(@RequestBody List<Long> ids) throws  Exception{
    return userService.deleteUser(ids).get();
  }
}

