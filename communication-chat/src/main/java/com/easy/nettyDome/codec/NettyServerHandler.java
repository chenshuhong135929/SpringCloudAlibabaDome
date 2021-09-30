package com.easy.nettyDome.codec;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import sun.plugin2.message.Message;

import java.util.concurrent.TimeUnit;

/**
 * @Auther ChenShuHong
 * @Date 2021-09-16 14:18
 *  说明
 *  1，我们自定义一个handler需要继承netty定义好的HandlerAdapter（规范）
 *  2，这时我们自定义的一个Handler，才能称为一个Handler
 */
//public class NettyServerHandler extends ChannelInboundHandlerAdapter {
public class NettyServerHandler extends SimpleChannelInboundHandler<StudentPOJO.Student> {

  /**
   *
   * @param ctx
   * @param msg
   * @throws Exception
   * ChannelHandlerContext 上下文对象，含有管道pipeline ,通道channel，地址
   * Object msg 就是客户端发送的数据，默认就是object
   *
   */
/*  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

    *//**
     * 读取从客户端发送的StudentPOJO.student
     *
     *//*
    StudentPOJO.Student student = (StudentPOJO.Student) msg;

    System.out.println("客户端发送的数据 id=" + student.getId() + student.getName());

    ctx.writeAndFlush("ok");
  }*/

  //数据读取完毕
  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    //将数据写入给缓存，并刷新
    ctx.writeAndFlush(Unpooled.copiedBuffer("hello  客户端~1 ",CharsetUtil.UTF_8));

  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

    ctx.close();
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, StudentPOJO.Student msg) throws Exception {
    System.out.println("客户端发送的数据 id=" + msg.getId() + msg.getName());

    ctx.writeAndFlush("ok");
  }
}
