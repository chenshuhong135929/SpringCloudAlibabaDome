package com.easy.nettyDome.inboundhandlerandoutboundhardler;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @Auther ChenShuHong
 * @Date 2021-10-08 10:40
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {

  @Override
  protected void initChannel(SocketChannel ch) throws Exception {
    System.out.println("MyClientInitializer 被调用");
    ChannelPipeline pipeline = ch.pipeline();
    pipeline.addLast(new MyByteToLongEncoder());
   // pipeline.addLast(new MyByteToLongDecoder());
    pipeline.addLast(new MyByteToLongDecoder2());
    pipeline.addLast(new MyClientHandler());
  }
}
