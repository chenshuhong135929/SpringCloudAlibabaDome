package com.easy.nettyDome.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @Auther ChenShuHong
 * @Date 2021-09-28 15:44
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {



  @Override
  public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    if(evt  instanceof IdleStateEvent){
      //将evt向下转型 IdleStateEvent
      String eventType="";
      IdleStateEvent event = (IdleStateEvent) evt;
      switch (event.state()){
        case READER_IDLE:
          eventType="读空闲";
          break;
        case WRITER_IDLE:
          eventType="写空闲";
          break;
        case ALL_IDLE:
          eventType="读写空闲";
          break;
      }
      System.out.println(ctx.channel().remoteAddress() +"--超时事件--"+eventType);
      /**
       * 在这里可以做一些操作，比如客户端太久没有读写操作可进行关闭或者其它操作
       */
    }
  }
}
