package com.easy.nettyDome.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @Auther ChenShuHong
 * @Date 2021-09-28 14:27
 */
public class MyServer {

  public static void main(String[] args) throws InterruptedException {
    NioEventLoopGroup bossGroup = new NioEventLoopGroup();
    NioEventLoopGroup workerGroup = new NioEventLoopGroup();

    try {
      ServerBootstrap bootstrap = new ServerBootstrap();
      bootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class)
          //添加一个boss日志处理器
          .handler(new LoggingHandler(LogLevel.INFO))
          .childHandler(new ChannelInitializer<SocketChannel>() {
        @Override
        protected void initChannel(SocketChannel ch) throws Exception {

          ChannelPipeline pipeline = ch.pipeline();
          //加入一个netty提供IdleStateHandler
          /**
           *  说明
           *  1，IdleStateHandler  是netty提供的处理器空闲状态的处理器
           *  2，Long  readerIdleTime  表示多长时间没有读，就会发送一个心跳检测包（检查是否还是链接状态）
           *  3，Long  writerIdleTime  表示多长时间没有写，就会发送一个心跳检测包（检查是否还是链接状态）
           *  4，Long  allIdleTime     表示多长时间没有读写，就会发送一个心跳检测包（检查是否还是链接状态）
           *  5，当IdleStateEvent  触发后就会传递给我们的管道的下一个Handler 去处理
           *    通过回掉触发下一个handler的serEventTiggered,在该方法中去处理IdlestateEvent
           */
          pipeline.addLast(new IdleStateHandler(3,5,7, TimeUnit.SECONDS));
          //加入一个对空闲检测进一步处理的handler（自定义）
          pipeline.addLast(new MyServerHandler());

        }
      });

      ChannelFuture channelFuture = bootstrap.bind(6666).sync();
      if(channelFuture.isSuccess()){
        System.out.println("server  star  ");
      }

      channelFuture.channel().closeFuture().sync();

    }finally {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();

    }

  }

}
