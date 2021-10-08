package com.easy.nettyDome.inboundhandlerandoutboundhardler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Auther ChenShuHong
 * @Date 2021-10-08 10:38
 */
public class MyClientHandler extends SimpleChannelInboundHandler<Long> {
  @Override
  protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
    System.out.println("服务器的ip="+ ctx.channel().remoteAddress());
    System.out.println("收到服务器消息="+ msg);
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {

    System.out.println("MyClientHandler 发送数据");
    ctx.writeAndFlush(123456l);
    /**
     * 分析
     * 1，"abcdabcdabcdabcd”是16个字节
     * 2，该处理器前一个handler是MyLongToByteEncoder
     * 3,MyLongToByteEncoder 父类MessageToByteEncoder
     * 4,父类MessageToByteEncoder方法write （该方法判断了当前msg是不是应该处理的类型，如果是就处理，不是就跳过encode）
     */
  }
}
