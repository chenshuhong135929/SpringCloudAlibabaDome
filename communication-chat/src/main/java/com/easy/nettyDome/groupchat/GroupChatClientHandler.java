package com.easy.nettyDome.groupchat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Auther ChenShuHong
 * @Date 2021-09-27 17:09
 */
public class GroupChatClientHandler extends SimpleChannelInboundHandler<String > {
  @Override
  protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
    System.out.println("客户端接收到数据"+msg);
  }
}
