package com.wisdomcommand.communication.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.communication.common.CommonResult;
import com.wisdomcommand.communication.entity.User;
import com.wisdomcommand.communication.mapper.UserMapper;
import com.wisdomcommand.communication.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @Auther ChenShuHong
 * @Date 2020-06-03 14:02
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

  @Autowired
  private  UserMapper userMapper;

  @Autowired
  PasswordEncoder passwordEncoder;
  @Override
  public User selectByUserName(String userName) {
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("user_name",userName);
    return userMapper.selectOne(queryWrapper);
  }

  @Override
  public  CompletableFuture<CommonResult>addUser(User user) {

    CommonResult result = new CommonResult();

    CompletableFuture<CommonResult> future = CompletableFuture.supplyAsync(() -> {
      if(StrUtil.isEmpty(user.getPassWord())){
        result.setCode(HttpStatus.HTTP_INTERNAL_ERROR);
        result.setMessage("你好，请输入密码。");
        return result;
      }
      if(StrUtil.isEmpty(user.getUserName())){
        result.setCode(HttpStatus.HTTP_INTERNAL_ERROR);
        result.setMessage("你好，请输入名称。");
        return result;
      }
      if(ObjectUtil.isNotEmpty(selectByUserName(user.getUserName()))){
        result.setCode(HttpStatus.HTTP_INTERNAL_ERROR);
        result.setMessage("你好，该用户名称已被使用["+user.getUserName()+"]请重新输入名称。");
        return result;
      }
      user.setPassWord(passwordEncoder.encode(user.getPassWord()));
      user.setRole("ADMIN");
      if (userMapper.insert(user) > 0) {
        result.setCode(HttpStatus.HTTP_OK);
        result.setMessage("添加用户成功。");
      } else {
        result.setCode(HttpStatus.HTTP_INTERNAL_ERROR);
        result.setMessage("添加失败。");
      }
      return result;
    });
    future.exceptionally((e) -> {
      e.printStackTrace();
      result.setCode(HttpStatus.HTTP_INTERNAL_ERROR);
      log.error("授权用户添加出错["+e.getMessage() +"]");
      result.setMessage("系统出错误，请联系管理员。");
      return result;
    });
    return future;
  }

  @Override
  public CompletableFuture<CommonResult> deleteUser(List<Long> ids) {

    CommonResult result = new CommonResult();
    CompletableFuture<CommonResult> future = CompletableFuture.supplyAsync(() -> {
        userMapper.deleteBatchIds(ids);
        result.setCode(HttpStatus.HTTP_OK);
        result.setMessage("删除用户成功。");
      return result;
    });

    future.exceptionally((e) -> {
      e.printStackTrace();
      result.setCode(HttpStatus.HTTP_INTERNAL_ERROR);
      log.error("授权用户删除出错["+e.getMessage() +"]");
      result.setMessage("系统出错误，请联系管理员。");
      return result;
    });
    return future;
  }
}
