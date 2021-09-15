package com.communication.email;

/**
 * @Auther ChenShuHong
 * @Date 2021-03-22 16:08
 */
public interface EmailService {

  /**
   * 邮箱发送
   * @param mail
   */
  void emailSend(String mail, String code);
}
