package com.easy.nettyDome.codec2;

import com.easy.nettyDome.codec.StudentPOJO;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Random;

/**
 * @Auther ChenShuHong
 * @Date 2021-09-16 16:52
 */
public class NettyClientHandler  extends ChannelInboundHandlerAdapter {
  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {

    /**
     * 随机的发送Student或者Workder对象
     *
     */
    int random = new Random().nextInt(3);
    MyDataInfo.MyMessage myMessage = null;
    if(0==random){//发送Student 对象
      myMessage= MyDataInfo.MyMessage.newBuilder().setDateType(MyDataInfo.MyMessage.DataType.StudentType)
          .setStudent(MyDataInfo.Student.newBuilder().setId(1).setName("StudentChenshuhong").build()).build();

    }else {
      myMessage= MyDataInfo.MyMessage.newBuilder().setDateType(MyDataInfo.MyMessage.DataType.WorkerType)
          .setWorker(MyDataInfo.Worker.newBuilder().setAge(100).setName("WorkerChenshuhong").build()).build();
    }
     ctx.writeAndFlush(myMessage);

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
