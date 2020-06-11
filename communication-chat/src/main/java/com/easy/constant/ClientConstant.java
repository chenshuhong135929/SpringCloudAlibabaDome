package com.easy.constant;

public enum ClientConstant {
  A("A","单点发送消息"),
  B("B","登录"),
  C("C","分组发送消息");

  private final String key;
  private final String value;


  ClientConstant(String key, String value) {
    this.key = key;
    this.value = value;
  }
  public String getKey() {
    return key;
  }
  public String getValue() {
    return value;
  }
}

