package com.easy.nettyDome.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @Auther ChenShuHong
 * @Date 2021-09-16 16:52
 */
public class NettyClientHandler  extends ChannelInboundHandlerAdapter {
  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {

    StudentPOJO.Student student = StudentPOJO.Student.newBuilder().setId(4).setName("陈树洪").build();

    ctx.writeAndFlush(student);

  }



  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

    ByteBuf  buf = (ByteBuf) msg;
    System.out.println("服务器回复的信息： " +  buf.toString(CharsetUtil.UTF_8));
    System.out.println("服务器的地址 ：  "  +  ctx.channel().remoteAddress());

  }


  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ctx.close();
  }
}
