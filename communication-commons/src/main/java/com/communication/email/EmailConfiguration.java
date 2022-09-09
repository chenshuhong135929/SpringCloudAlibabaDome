package com.communication.email;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * @Auther ChenShuHong
 * @Date 2021-03-22 16:12
 */
@Configuration
public class EmailConfiguration {


  @Bean
  @ConditionalOnMissingBean({EmailService.class})
  @ConditionalOnProperty(prefix = "spring.mail",value = "enable",havingValue = "true",matchIfMissing = false)
  EmailService emailService(JavaMailSender javaMailSender){
    return new EmailServiceImpl(javaMailSender);
  }


}
