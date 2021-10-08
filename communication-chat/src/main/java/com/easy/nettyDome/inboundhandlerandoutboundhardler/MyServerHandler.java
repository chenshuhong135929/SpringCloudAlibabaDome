package com.easy.nettyDome.inboundhandlerandoutboundhardler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Auther ChenShuHong
 * @Date 2021-10-08 10:13
 */
public class MyServerHandler extends SimpleChannelInboundHandler<Long> {
  @Override
  protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
    System.out.println("从客户端"+ ctx.channel().remoteAddress()+"  msg  : "+msg);
    //给客户端发送数据
    ctx.writeAndFlush(11111l);
  }
}
