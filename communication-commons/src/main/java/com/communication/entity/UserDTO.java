package com.communication.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Auther ChenShuHong
 * @Date 2021-07-05 16:12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserDTO {
  private String  id;
  private String username;
  private String password;
  private List<String> roles;
}
