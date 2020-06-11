package com.wisdomcommand.communication.controller;

import com.wisdomcommand.communication.entity.User;
import com.wisdomcommand.communication.mapper.UserMapper;
import com.wisdomcommand.communication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @Auther ChenShuHong
 * @Date 2020-06-03 11:37
 */
@RestController
@RequestMapping("user")
public class UserController {
  @Autowired
  public UserService userService;


  @GetMapping("getByName")
  public User getByName(){
    return userService.selectByUserName("zhangjian");
  }

  /**
   * 获取授权的用户信息
   * @param principal 当前用户
   * @return 授权信息
   */
  @GetMapping("current")
  public Principal user(Principal principal){

    return principal;
  }
}

