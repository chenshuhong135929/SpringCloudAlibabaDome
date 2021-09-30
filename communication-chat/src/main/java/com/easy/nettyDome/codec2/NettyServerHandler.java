package com.easy.nettyDome.codec2;

import com.easy.nettyDome.codec.StudentPOJO;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @Auther ChenShuHong
 * @Date 2021-09-16 14:18
 *  说明
 *  1，我们自定义一个handler需要继承netty定义好的HandlerAdapter（规范）
 *  2，这时我们自定义的一个Handler，才能称为一个Handler
 */
//public class NettyServerHandler extends ChannelInboundHandlerAdapter {
public class NettyServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {

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
  protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {
    MyDataInfo.MyMessage.DataType dateType = msg.getDateType();
    if(dateType==MyDataInfo.MyMessage.DataType.StudentType){
      MyDataInfo.Student student = msg.getStudent();
      System.out.println("客户端： 学生 "+student.getName());
    }else  if(dateType==MyDataInfo.MyMessage.DataType.WorkerType){
      MyDataInfo.Worker worker = msg.getWorker();
      System.out.println("客户端： 老师 "+worker.getName());
    }else {
      System.out.println("类型错误");
    }

    ctx.writeAndFlush("ok");
  }
}
