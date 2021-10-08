package com.easy.nettyDome.inboundhandlerandoutboundhardler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;


/**
 * @Auther ChenShuHong
 * @Date 2021-10-08 10:30
 */
public class MyClient {

  public static void main(String[] args) throws InterruptedException {
    NioEventLoopGroup group = new NioEventLoopGroup();
    try {
      Bootstrap bootstrap = new Bootstrap();
      ChannelFuture channelFuture = bootstrap.group(group)
          .channel(NioSocketChannel.class)
          .handler(new MyClientInitializer())
          .connect("localhost", 8080).sync();
      channelFuture.channel().closeFuture().sync();
    }finally {
      group.shutdownGracefully();
    }


  }

}
