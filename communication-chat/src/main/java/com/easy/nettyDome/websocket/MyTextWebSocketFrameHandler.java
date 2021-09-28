package com.easy.nettyDome.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;

/**
 * @Auther ChenShuHong
 * @Date 2021-09-28 16:47
 * 这里TextWebSocketFrame类型，表示一个文本帧（frame）
 */
public class MyTextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
  @Override
  protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {

    System.out.println("服务器收到消息"+  msg.text());
    System.out.println("channelRead0  发送"+ctx.channel().id().asLongText());
    //回复信息
    ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器时间"+ LocalDateTime.now())+ msg.text());
  }

  @Override
  public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

    //id表示唯一的值LongText是惟一的
    System.out.println("handlerAdded被调用"+ctx.channel().id().asLongText());

  }

  @Override
  public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

    System.out.println("handlerAdded被调用"+ctx.channel().id().asLongText());
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    System.out.println("异常发生"+cause.getMessage());
    ctx.close();
  }
}
