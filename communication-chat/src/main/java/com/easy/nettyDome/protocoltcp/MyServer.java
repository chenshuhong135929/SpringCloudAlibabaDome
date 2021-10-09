package com.easy.nettyDome.protocoltcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Auther ChenShuHong
 * @Date 2021-10-08 17:18
 * TCP粘包和拆包基本介绍
 * 1，TCP是面向连接的，面向流的，提供高可靠性服务，收发两端（客户端服务器端）
 *    都要有一一成对的socket,因此，发送端为了将多个发送接收的包，更有效的发给对方
 *    ，使用了优化方法（Nagle算法），将多次间隔较小且数量小的数据，合并成一个大的
 *    数据块，然后进行封包，這样做虽然提高了效率，但是接收端就难于分辨出完整的数据
 *    包了，因为面向流的通信时无消息保护边界的。
 * 2，由于TCP无消息保护边界，需要在接收端处理消息边界问题，也就是我们所说的粘包，
 *    拆包问题。
 *
 *    自定义协议包可以解决粘包问题
 *    MessageProtocol  自定义协议
 *    MyMessageEncoder 编码（编码继承类 MessageToByteEncoder）
 *    MyMessageDeCoder 解码（解码继承类 ReplayingDecoder）
 */
public class MyServer {
  public static void main(String[] args) throws InterruptedException {
    NioEventLoopGroup bossGroup   = new NioEventLoopGroup();
    NioEventLoopGroup workerGroup = new NioEventLoopGroup();

    try {
      ServerBootstrap serverBootstrap = new ServerBootstrap();
      ServerBootstrap bootstrap = serverBootstrap.group(bossGroup, workerGroup);
      ChannelFuture future = bootstrap.channel(NioServerSocketChannel.class)
          .childHandler(new MyServerInitializer())
          .bind(8080).sync();
      if(future.isSuccess()){
        System.out.println("start server port 8080");
      }

      future.channel().closeFuture().sync();


    }finally {
      bossGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();
    }

  }
}
