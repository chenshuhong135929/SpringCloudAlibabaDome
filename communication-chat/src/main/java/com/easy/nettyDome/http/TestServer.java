package com.easy.nettyDome.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;


/**
 * @Auther ChenShuHong
 * @Date 2021-09-23 11:25
 */
public class TestServer {


  public static void main(String[] args) throws InterruptedException {
    EventLoopGroup  bossGroup = new NioEventLoopGroup();
    EventLoopGroup  workerGroup = new NioEventLoopGroup();

    try{

      ServerBootstrap bootstrap = new ServerBootstrap();
      bootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class).childHandler (new TestServerInitializer());
      //绑定一个端口，并且同步处理，生成一个ChannelFuture对象
      ChannelFuture channelFuture = bootstrap.bind(8080).sync();
      //给ChannelFuture 注册监听器，监控我们关心的事件
      channelFuture.addListener(
          (ChannelFutureListener) future -> {
            if(channelFuture.isSuccess()){
              System.out.println("监听端口  6668  成功");
            }else{
              System.out.println("监听端口失败");
            }
          }
      );
      //对关闭通道进行监听
      channelFuture.channel().closeFuture().sync();
    }  finally {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

  }

}
