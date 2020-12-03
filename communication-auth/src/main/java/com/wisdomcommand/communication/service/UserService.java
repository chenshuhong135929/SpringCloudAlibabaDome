package com.wisdomcommand.communication.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.communication.common.CommonResult;
import com.wisdomcommand.communication.entity.User;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @Auther ChenShuHong
 * @Date 2020-06-03 14:01
 */
public interface  UserService  extends IService<User> {


  User selectByUserName(String userName);

  /**
   * 添加用户
   * @param user
   * @return
   */
  CompletableFuture<CommonResult>addUser(User user);

  /**
   * 删除用户
   * @param ids
   * @return
   */
  CompletableFuture<CommonResult>deleteUser(List<Long> ids);
}
