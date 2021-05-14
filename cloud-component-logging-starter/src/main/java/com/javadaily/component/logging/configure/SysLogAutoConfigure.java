package com.javadaily.component.logging.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther ChenShuHong
 * @Date 2021-05-14 11:09
 */
@Configuration
public class SysLogAutoConfigure {

  @Bean
  public SysLogAspect controllerLogAspect(){
    return new SysLogAspect();
  }

}
