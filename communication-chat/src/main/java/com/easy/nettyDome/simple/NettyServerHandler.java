package com.easy.nettyDome.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

/**
 * @Auther ChenShuHong
 * @Date 2021-09-16 14:18
 *  说明
 *  1，我们自定义一个handler需要继承netty定义好的HandlerAdapter（规范）
 *  2，这时我们自定义的一个Handler，才能称为一个Handler
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {


  /**
   *
   * @param ctx
   * @param msg
   * @throws Exception
   * ChannelHandlerContext 上下文对象，含有管道pipeline ,通道channel，地址
   * Object msg 就是客户端发送的数据，默认就是object
   *
   */
  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

    System.out.println("服务器读取线程 " +  Thread.currentThread().getName());
    System.out.println("server ctx = "+ ctx);
    System.out.println("看看channel和pipeline 的关系");
    Channel channel = ctx.channel();
    ChannelPipeline pipeline = ctx.pipeline();//本质就是一个双向链接
    System.out.println();
    //将msg转成一个ByteBuf
    //用的是netty提供的ByteBuf  不是我们Nio提供的ByteBuffer
    ByteBuf  buf = (ByteBuf)msg;
    System.out.println("客户端发送消息是： "+ buf.toString(CharsetUtil.UTF_8));
    System.out.println("客户端地址"+channel.remoteAddress());
  }

  //数据读取完毕
  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    //将数据写入给缓存，并刷新
    ctx.writeAndFlush(Unpooled.copiedBuffer("hello  客户端~ ",CharsetUtil.UTF_8));

  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

    ctx.close();
  }
}
