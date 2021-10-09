package com.easy.nettyDome.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;


/**
 * @Auther ChenShuHong
 * @Date 2021-10-08 17:37
 */
public class MyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    System.out.println("发送消息");
    //向客户端发送10次hello,server
    for(int i =0;i<10;i++){
      ctx.writeAndFlush(Unpooled.copiedBuffer("hello,server \n", CharsetUtil.UTF_8));
    }
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

    byte [] buff = new byte[msg.readableBytes()];
    msg.readBytes(buff);
    System.out.println(new String(buff,CharsetUtil.UTF_8));

  }
}
