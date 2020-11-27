package com.wisdomcommand.communication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

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
        .build() /* 设置安全模式，swagger可以设置访问token */
        .securitySchemes(securitySchemes())
        .securityContexts(securityContexts());
  }
  /**
   * 安全模式，这里指定token通过Authorization头请求头传递
   */
  private List<ApiKey> securitySchemes()
  {
    List<ApiKey> apiKeyList = new ArrayList<ApiKey>();
    apiKeyList.add(new ApiKey("Authorization", "Authorization", "header"));
    return apiKeyList;
  }
  /**
   * 安全上下文
   */
  private List<SecurityContext> securityContexts()
  {
    List<SecurityContext> securityContexts = new ArrayList<>();
    securityContexts.add(
        SecurityContext.builder()
            .securityReferences(defaultAuth())
            .forPaths(PathSelectors.regex("^(?!auth).*$"))
            .build());
    return securityContexts;
  }
  /**
   * 默认的安全上引用
   */
  private List<SecurityReference> defaultAuth()
  {
    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    List<SecurityReference> securityReferences = new ArrayList<>();
    securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
    return securityReferences;
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
