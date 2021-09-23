package com.easy.nettyDome.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;


/**
 * @Auther ChenShuHong
 * @Date 2021-09-16 15:33
 */
public class NettyClient {
  public static void main(String[] args) throws InterruptedException {


    //客户端需要一个事件循环组
    EventLoopGroup  group = new NioEventLoopGroup();

    //创建客户端启动对象
    //注意客户端使用的不是ServerBootStrap 而是BootStrap
    Bootstrap bootstrap = new Bootstrap();

    try {


    //设置相关参数
    bootstrap.group(group)//设置线程组
    .channel(NioSocketChannel.class)//设置客户端通道实现类
    .handler(new ChannelInitializer<SocketChannel>() {
      @Override
      protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(new NettyClientHandler());
      }
    });

    System.out.println("客户端ok...");
    ChannelFuture cf = bootstrap.connect("localhost", 6668).sync();
    //给关闭通道进行监听
    cf.channel().closeFuture().sync();
    //给ChannelFuture 注册监听器，监控我们关心的事件
    cf.addListener(
        (ChannelFutureListener) future -> {
           if(cf.isSuccess()){
             System.out.println("连接服务器端成功");
           }
        }
    );
    }finally {
      group.shutdownGracefully();
    }
  }
}
