package com.easy.nettyDome.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * @Auther ChenShuHong
 * @Date 2021-10-08 17:42
 */
public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
  @Override
  protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
    byte[] buff = new byte[msg.readableBytes()];
    msg.readBytes(buff);
    System.out.println("接收到客户端信息：="+  new String(buff, Charset.forName("utf-8")));
    ctx.writeAndFlush(Unpooled.copiedBuffer("hello,client \n", CharsetUtil.UTF_8));
  }
}
