package com.wisdomcommand.communication.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wisdomcommand.communication.entity.User;

/**
 * @Auther ChenShuHong
 * @Date 2020-06-03 14:01
 */
public interface  UserService  extends IService<User> {


  User selectByUserName(String userName);
}
