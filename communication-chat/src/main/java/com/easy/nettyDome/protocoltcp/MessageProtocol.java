package com.easy.nettyDome.protocoltcp;

/**
 * @Auther ChenShuHong
 * @Date 2021-10-09 10:23
 * 协议包
 */
public class MessageProtocol {

  private int len;
  private byte[] content;

  public int getLen() {
    return len;
  }

  public void setLen(int len) {
    this.len = len;
  }

  public byte[] getContent() {
    return content;
  }

  public void setContent(byte[] content) {
    this.content = content;
  }
}
