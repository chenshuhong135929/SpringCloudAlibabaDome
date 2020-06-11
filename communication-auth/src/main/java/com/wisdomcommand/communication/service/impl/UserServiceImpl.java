package com.wisdomcommand.communication.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wisdomcommand.communication.entity.User;
import com.wisdomcommand.communication.mapper.UserMapper;
import com.wisdomcommand.communication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Auther ChenShuHong
 * @Date 2020-06-03 14:02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

  @Autowired
  private  UserMapper userMapper;

  @Override
  public User selectByUserName(String userName) {
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("user_name",userName);
    return userMapper.selectOne(queryWrapper);
  }
}
