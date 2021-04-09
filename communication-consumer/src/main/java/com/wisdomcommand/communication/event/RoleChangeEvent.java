package com.wisdomcommand.communication.event;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Auther ChenShuHong
 * @Date 2021-04-09 10:29
 */
@Data
@AllArgsConstructor
public class RoleChangeEvent {

  private String roleId;
}
