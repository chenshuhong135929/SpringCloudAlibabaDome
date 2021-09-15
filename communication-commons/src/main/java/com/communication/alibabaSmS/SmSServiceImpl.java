package com.communication.alibabaSmS;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * @Auther ChenShuHong
 * @Date 2021-03-22 16:01
 */

public class SmSServiceImpl implements SmSService {


  private AlibabaSmSProperties alibabaSmSProperties;

  public SmSServiceImpl(AlibabaSmSProperties alibabaSmSProperties) {
    this.alibabaSmSProperties = alibabaSmSProperties;
  }

  /**
   * 短信发送
   * @throws ClientException
   */
  @Override
 public  void  sendSmS(String phone,String code) throws ClientException {
//可自助调整超时时间
    System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
    System.setProperty("sun.net.client.defaultReadTimeout", "10000");
    //初始化acsClient,暂不支持region化
    IClientProfile profile= DefaultProfile.getProfile("cn-hangzhou", alibabaSmSProperties.accessKeyId, alibabaSmSProperties.accessKeySecret);
    DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", alibabaSmSProperties.product, alibabaSmSProperties.domain);
    IAcsClient acsClient=new DefaultAcsClient(profile);
    //组装请求对象-具体描述见控制台-文档部分内容
    SendSmsRequest request=new SendSmsRequest();
    //必填:待发送手机号
    request.setPhoneNumbers(phone);
    //必填:短信签名-可在短信控制台中找到举个例子
    request.setSignName(alibabaSmSProperties.signName);
    //必填:短信模板id-可在短信控制台中找到，是id不是名字，举个例子
    request.setTemplateCode(alibabaSmSProperties.templateCode);
    //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为--必填，与模板相对应
    //request.setTemplateParam("{\"name\":\"Tom\", \"code\":\"123\"}");
    request.setTemplateParam("{\"code\":\""+ code+"\"}");
    //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
    //request.setSmsUpExtendCode("90997");
    //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
    //request.setOutId("yourOutId");
    //hint 此处可能会抛出异常，注意catch
    SendSmsResponse sendSmsResponse=acsClient.getAcsResponse(request);
    // System.out.println(sendSmsResponse.toString());

  }

}
