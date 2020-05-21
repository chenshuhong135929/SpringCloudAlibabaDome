package com.wisdomcommand.communication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
  private static final String VERSION = "1.0.0";
  /**
   * 创建API
   */
  @Bean
  public Docket createRestApi(){
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .select()
        //指定接口包所在路径
        .apis(RequestHandlerSelectors.basePackage("com.wisdomcommand.communication.controller"))
        .paths(PathSelectors.any())
        .build();
  }

  /**
   * 添加摘要信息
   */
  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("communication-consumer接口文档")
        .contact(new Contact("CHENSHUHONG","http://javadaily.cn","jianzh5@163.com"))
        .description("product-server接口文档")
        .termsOfServiceUrl("http://javadaily.cn")
        .license("The Apache License, Version 2.0")
        .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
        .version(VERSION)
        .build();
  }
}
