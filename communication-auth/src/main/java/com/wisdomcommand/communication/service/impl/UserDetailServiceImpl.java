package com.wisdomcommand.communication.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wisdomcommand.communication.entity.User;
import com.wisdomcommand.communication.mapper.UserMapper;
import com.wisdomcommand.communication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther ChenShuHong
 * @Date 2020-06-03 13:27
 */
@Service
public class UserDetailServiceImpl extends ServiceImpl<UserMapper,User> implements UserDetailsService {

  @Override
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

   User user =this.lambdaQuery()
        .eq(User::getUserName, userName).one();
    List<GrantedAuthority> authorities =AuthorityUtils.commaSeparatedStringToAuthorityList( user.getRole());
      return new org.springframework.security.core.userdetails.User(user.getUserName(),user.getPassWord(),authorities);
  }

}
