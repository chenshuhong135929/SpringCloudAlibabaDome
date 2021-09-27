package com.easy.nettyDome.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;


/**
 * 说明
 * SimpleChannelInboundHandler  是  ChannelInboundHandlerAdapter
 * HttpObject  客户端和服务器端相互通讯的数据封装成HttpObject
 * @Auther ChenShuHong
 * @Date 2021-09-24 11:01
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

  //ChannelRead0读取客户端数据
  @Override
  protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {

    //判断msg是不是httpRequest请求
    if(msg instanceof HttpRequest){
      System.out.println("msg 类型"+ msg.getClass());
      System.out.println("客户地址"+ctx.channel().remoteAddress());

      if("/favicon.ico".equals(msg.getUri())){
        System.out.println("你请求的 favicon.ico,不做相应");
        return;
      }

      //回复信息给浏览器【http协议】
      ByteBuf content = Unpooled.copiedBuffer("Hello ,我是服务器", CharsetUtil.UTF_8);
      //构造一个Http的相应，即HttpResponse
      FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
      response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain;charset=UTF-8");
      response.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());

      //将构建好的response返回
      ctx.writeAndFlush(response);
    }
  }
}

