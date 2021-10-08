package com.easy.nettyDome.inboundhandlerandoutboundhardler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Auther ChenShuHong
 * @Date 2021-10-08 10:43
 */
public class MyByteToLongEncoder extends MessageToByteEncoder<Long> {
  //编码方式
  @Override
  protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
    System.out.println("MyByteToLongEncoder encode 被调用");
    System.out.println("msg  "+msg);
    out.writeLong(msg);
  }
}
