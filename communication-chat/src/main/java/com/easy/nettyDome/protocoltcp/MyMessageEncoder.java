package com.easy.nettyDome.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Auther ChenShuHong
 * @Date 2021-10-09 10:37
 */
public class MyMessageEncoder  extends MessageToByteEncoder<MessageProtocol> {
  @Override
  protected void encode(ChannelHandlerContext ctx, MessageProtocol msg, ByteBuf out) throws Exception {
    System.out.println("MyMessageEncoder encoder 被调用");
    out.writeInt(msg.getLen());
    out.writeBytes(msg.getContent());
  }
}
