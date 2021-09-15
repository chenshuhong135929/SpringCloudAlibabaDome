package com.communication.alibabaSmS;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @Auther ChenShuHong
 * @Date 2021-03-22 15:20
 */
@ConfigurationProperties(prefix = "alibaba.sms")
@Data
@RefreshScope
public class AlibabaSmSProperties {

  //产品名称:云通信短信API产品,开发者无需替换
  String product;
  //产品域名,开发者无需替换
  String domain;
  // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)，下面举个例子
  String accessKeyId;
  String accessKeySecret;
  //短信签名-可在短信控制台中找
  String signName ;
  //短信模板id-可在短信控制台中找到
  String templateCode ;

}
