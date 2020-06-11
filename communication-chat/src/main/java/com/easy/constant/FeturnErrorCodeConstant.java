package com.easy.constant;

public enum  FeturnErrorCodeConstant {
  Error_401("401","分组不存在！！！") ;

  private final String key;
  private final String value;


  FeturnErrorCodeConstant(String key, String value) {
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
