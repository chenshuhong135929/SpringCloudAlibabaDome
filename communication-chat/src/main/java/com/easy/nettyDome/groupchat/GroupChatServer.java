package com.easy.nettyDome.groupchat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @Auther ChenShuHong
 * @Date 2021-09-27 15:02
 */
public class GroupChatServer {



  private int port;//监听端口

  public GroupChatServer(int port){
    this.port = port;
  }

  //编写run方法，处理客户端的请求
  public void run() throws InterruptedException {
    NioEventLoopGroup bossGroup = new NioEventLoopGroup();
    NioEventLoopGroup workerGroup  = new NioEventLoopGroup();

    try {
      ServerBootstrap bootstrap = new ServerBootstrap();
      bootstrap.group(bossGroup,workerGroup)
          .channel(NioServerSocketChannel.class)
          .option(ChannelOption.SO_BACKLOG,128)
          .childOption(ChannelOption.SO_KEEPALIVE,true)
          .childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
              ChannelPipeline pipeline = ch.pipeline();
              /**
               *  说明：
               *  codec(编码器)的组成部分有两个，decoder(解码器)和encoder（编码器），
               *  encoder负责把业务数据转换成字节数据，decoder负责把字节数据转换成业务数据
               */
              //加入解码器
              pipeline.addLast("decoder",new StringDecoder());
              //编码器
              pipeline.addLast("encoder",new StringEncoder());
              //业务类
              pipeline.addLast(new GroupChatServerHandler());
            }
          });


      ChannelFuture sync = bootstrap.bind(port).sync();
      if(sync.isSuccess()){
        System.out.println("start   server ");
      }
      sync.channel().closeFuture().sync();
    }finally {
      bossGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();

    }

  }


  public static void main(String[] args) throws InterruptedException {
    new GroupChatServer(6666).run();
  }
}
