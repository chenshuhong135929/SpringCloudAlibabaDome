package com.easy.nettyDome.protocoltcp;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

/**
 * @Auther ChenShuHong
 * @Date 2021-10-08 17:40
 */
public class MyServerInitializer extends ChannelInitializer {
  @Override
  protected void initChannel(Channel ch) throws Exception {
    ChannelPipeline pipeline = ch.pipeline();
    pipeline.addLast(new MyMessageEncoder());
    pipeline.addLast(new MyMessageDecoder());
    pipeline.addLast(new MyServerHandler());
  }
}
