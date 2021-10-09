package com.easy.nettyDome.tcp;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * @Auther ChenShuHong
 * @Date 2021-10-08 17:40
 */
public class MyServerInitializer extends ChannelInitializer {
  @Override
  protected void initChannel(Channel ch) throws Exception {
    ChannelPipeline pipeline = ch.pipeline();
    pipeline.addLast(new MyServerHandler());
   // pipeline.addLast(new LineBasedFrameDecoder(1024));

  }
}
