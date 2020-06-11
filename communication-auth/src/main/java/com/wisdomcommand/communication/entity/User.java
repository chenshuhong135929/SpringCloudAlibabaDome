package com.wisdomcommand.communication.entity;

import lombok.Data;

/**
 * @Auther ChenShuHong
 * @Date 2020-06-03 13:43
 */
@Data
public class User {
  private long id;

  private String passWord;

  private String userName;

  private String  role;
}
