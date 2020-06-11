package com.wisdomcommand.communication.service.impl;

import com.wisdomcommand.communication.entity.User;
import com.wisdomcommand.communication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Auther ChenShuHong
 * @Date 2020-06-03 13:27
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

  @Autowired
  private  UserService userService;

  @Override
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    //获取本地用户
    User user = userService.selectByUserName(userName);
    if(user != null){
      //返回oauth2的用户
      return new org.springframework.security.core.userdetails.User(
          user.getUserName(),
          user.getPassWord(),
          AuthorityUtils.createAuthorityList(user.getRole())) ;
    }else{
      throw  new UsernameNotFoundException("用户["+userName+"]不存在");
    }
  }

}
