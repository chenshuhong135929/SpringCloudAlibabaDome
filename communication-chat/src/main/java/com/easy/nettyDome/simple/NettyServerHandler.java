package com.easy.nettyDome.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

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

    //比如这里我们有一个非常耗时长的业务->异步执行->提交该channel 对应的NIOEventLoop的taskQueue中
    //解决方案1 用户程序自定义的普通任务
    ctx.channel().eventLoop().execute(()->{
      try {
        Thread.sleep(10*1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      ctx.writeAndFlush(Unpooled.copiedBuffer("hello  客户端~2 ",CharsetUtil.UTF_8));
    });



    //用户自定义定时任务-> 该任务是提交到scheduleTasKQueue 中
    ctx.channel().eventLoop().schedule(()->{
      try {
        Thread.sleep(10*1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      ctx.writeAndFlush(Unpooled.copiedBuffer("hello  客户端~3 ",CharsetUtil.UTF_8));
    },5, TimeUnit.SECONDS);
    System.out.println("go on ...");



   /* System.out.println("服务器读取线程 " +  Thread.currentThread().getName());
    System.out.println("server ctx = "+ ctx);
    System.out.println("看看channel和pipeline 的关系");
    Channel channel = ctx.channel();
    ChannelPipeline pipeline = ctx.pipeline();//本质就是一个双向链接
    System.out.println();
    //将msg转成一个ByteBuf
    //用的是netty提供的ByteBuf  不是我们Nio提供的ByteBuffer
    ByteBuf  buf = (ByteBuf)msg;
    System.out.println("客户端发送消息是： "+ buf.toString(CharsetUtil.UTF_8));
    System.out.println("客户端地址"+channel.remoteAddress());*/
  }

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
}
