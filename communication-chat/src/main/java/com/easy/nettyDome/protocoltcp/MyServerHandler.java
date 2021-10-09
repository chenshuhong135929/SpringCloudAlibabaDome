package com.easy.nettyDome.protocoltcp;


import cn.hutool.Hutool;
import cn.hutool.core.util.IdUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;

/**
 * @Auther ChenShuHong
 * @Date 2021-10-08 17:42
 */
public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {
  @Override
  protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
    System.out.println("长度： = "+msg.getLen());
    System.out.println("客户端信息： = "+new String(msg.getContent()));


    String str ="收到信息"+ IdUtil.fastSimpleUUID();

    MessageProtocol messageProtocol = new MessageProtocol();

    byte [] content =str.getBytes(CharsetUtil.UTF_8);
    int length = content.length;
    messageProtocol.setContent(content);
    messageProtocol.setLen(length);
    System.out.println("发送"+str);
    ctx.writeAndFlush(messageProtocol);
  }
}
