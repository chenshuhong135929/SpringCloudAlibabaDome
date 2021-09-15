package com.communication.alibabaSmS;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Auther ChenShuHong
 * @Date 2021-03-22 15:49
 */
@Configuration
@EnableConfigurationProperties({AlibabaSmSProperties.class})
public class SmSConfiguration {


  @Bean
  @ConditionalOnMissingBean({SmSService.class})
  SmSService smSService(AlibabaSmSProperties alibabaSmSProperties){
    return new SmSServiceImpl(alibabaSmSProperties);
  }

}
