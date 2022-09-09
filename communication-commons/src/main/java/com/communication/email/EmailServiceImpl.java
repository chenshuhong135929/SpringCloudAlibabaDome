package com.communication.email;



import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/**
 * @Auther ChenShuHong
 * @Date 2021-03-22 16:09
 */
@Slf4j
public class EmailServiceImpl implements EmailService {
  @Value("classpath:/verifyMailbox.html")
  Resource resource;

  String verifyMailbox;

  @Value("${m2mUrl: http://192.168.1.4:9000}")
  String m2mUrl;


  @Autowired
  MailProperties mailProperties;
  private JavaMailSender javaMailSender;

  public EmailServiceImpl(JavaMailSender javaMailSender) {
    this.javaMailSender = javaMailSender;
  }


  public void readResource()   {

    try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
      verifyMailbox = reader.lines().collect(Collectors.joining("\n"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }


  }
  @Override
  public void emailSend(String  mail,String code) {
    readResource();
    MimeMessage mimeMailMessage = null;
    try {
      mimeMailMessage = javaMailSender.createMimeMessage();
      MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
      mimeMessageHelper.setFrom(mailProperties.getUsername());
      mimeMessageHelper.setTo(mail);
      mimeMessageHelper.setSubject("HJBase");
      String emailHtml = verifyMailbox.replace("m2mLogo.png", m2mUrl + "/static/img/email_logo.101ba713.png" ).replace("m2mUrl", code);
      mimeMessageHelper.setText(emailHtml, true);
      javaMailSender.send(mimeMailMessage);
    } catch (Exception e) {
      log.error("邮件发送失败", e.getMessage());
    }
  }
}
