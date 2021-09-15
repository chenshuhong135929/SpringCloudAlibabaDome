package com.communication.email;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;

/**
 * @Auther ChenShuHong
 * @Date 2021-03-22 16:09
 */
@Slf4j
public class EmailServiceImpl implements EmailService {

  @Autowired
  MailProperties mailProperties;
  private JavaMailSender javaMailSender;

  public EmailServiceImpl(JavaMailSender javaMailSender) {
    this.javaMailSender = javaMailSender;
  }

  @Override
  public void emailSend(String  mail,String code) {

    MimeMessage mimeMailMessage = null;
    try {
      mimeMailMessage = javaMailSender.createMimeMessage();
      MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
      mimeMessageHelper.setFrom(mailProperties.getUsername());
      mimeMessageHelper.setTo(mail);
      mimeMessageHelper.setSubject("智能家居");
      StringBuilder sb = new StringBuilder();
      sb.append("<h1>深圳市物联微电子开发有限公司</h1>")
          .append("<p style='color:#F00'>验证码:"+code+"</p>")
          .append("<p style='text-align:right'>及时验证</p>");
      mimeMessageHelper.setText(sb.toString(), true);
      javaMailSender.send(mimeMailMessage);
    } catch (Exception e) {
      log.error("邮件发送失败", e.getMessage());
    }
  }
}
