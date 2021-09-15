package com.communication.alibabaSmS;


import com.aliyuncs.exceptions.ClientException;

public interface SmSService {
  /**
   * 短信验证码
   * @param  phone
   * @throws ClientException
   */
  void  sendSmS(String phone, String code) throws ClientException;
}
