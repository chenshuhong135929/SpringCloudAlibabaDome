package com.easy.nettyDome.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;


/**
 * @Auther ChenShuHong
 * @Date 2021-10-08 17:37
 */
public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    System.out.println("发送消息");
    //向客户端发送10次hello,server
    for(int i =0;i<10;i++){
    String msg = "你好，服务器端。";
      byte[] content = msg.getBytes(CharsetUtil.UTF_8);
      int length = content.length;
      //创建协议包对象
      MessageProtocol messageProtocol = new MessageProtocol();
      messageProtocol.setContent(content);
      messageProtocol.setLen(length);
      ctx.writeAndFlush(messageProtocol);
    }
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
    System.out.println("长度：+  "+msg.getLen());
    System.out.println("服务端数据：+  "+ new String(msg.getContent(),CharsetUtil.UTF_8));


  }
}
