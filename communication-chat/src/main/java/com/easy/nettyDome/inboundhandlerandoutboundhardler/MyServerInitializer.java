package com.easy.nettyDome.inboundhandlerandoutboundhardler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

/**
 * @Auther ChenShuHong
 * @Date 2021-10-08 10:23
 */
public class MyServerInitializer extends ChannelInitializer {
  @Override
  protected void initChannel(Channel ch) throws Exception {
    ChannelPipeline pipeline = ch.pipeline();
    //pipeline.addLast(new MyByteToLongDecoder());
    pipeline.addLast(new MyByteToLongDecoder2());
    pipeline.addLast(new MyByteToLongEncoder());
    pipeline.addLast(new MyServerHandler());
  }
}
