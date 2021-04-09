package com.wisdomcommand.communication.listener;


import com.wisdomcommand.communication.event.RoleChangeEvent;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


/**
 * @Auther ChenShuHong
 * @Date 2021-04-09 10:20
 */
@Component
@Slf4j
public class RoleListener {

/*  @Autowired
  SysUserService sysUserService;
  @Autowired
  SysRoleService sysRoleService;*/

  @EventListener
  @Async
  public void handleRoleChange(RoleChangeEvent event){
 /*   List<SysUser> users = sysUserService.roleIDsYsyUser(event.getRoleId());
    log.info("更新权限信息[" + users.size() + "]");
    if(users!=null&&users.size()>0){
      users.stream()
          .forEach(u->{
            sysUserService.saveToRedis(u);
          });
    }*/
  }
}
