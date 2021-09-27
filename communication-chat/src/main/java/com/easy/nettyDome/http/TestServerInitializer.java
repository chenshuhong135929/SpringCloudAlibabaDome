package com.easy.nettyDome.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @Auther ChenShuHong
 * @Date 2021-09-24 10:52
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
  @Override
  protected void initChannel(SocketChannel ch) throws Exception {

    //向管道加入处理器

    //得到管道
    //加入一个Netty提供的HttpServerCodec codec => [coder - decoder]
    //HttpServerCodec说明
    //1，HttpServerCodec是Netty提供的处理Http的 ，编码解码器
    ChannelPipeline pipeline = ch.pipeline();
    pipeline.addLast(new HttpServerCodec());
    pipeline .addLast("httpAggregator",new HttpObjectAggregator(512*1024)); // http 消息聚合器
    //2，增加自定义的Handler
    pipeline.addLast(new TestHttpServerHandler());
  }
}
