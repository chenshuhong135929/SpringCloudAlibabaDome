package com.easy.nettyDome.codec2;

import com.easy.nettyDome.codec.StudentPOJO;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

/**
 * @Auther ChenShuHong
 * @Date 2021-09-16 11:18
 */
public class NettyServer {


  public static void main(String[] args) throws InterruptedException {

/**
 * Protobuf基本介绍和使用示意图
 * 1，Protobuf是Google发布的开源项目，全称Google  Protocol  Buffers ，
 *    是一种轻便高效的结构性数据存储格式，可以用来结构化数据串行化
 *    ，或者说序列号，它很适合做数据存储或RPC数据交换格式
 * 2，Protobuf是以message的方式来管理数据的
 * 3，支持跨平台，跨语言，即【客户端喝服务器端可以是不同的语言编写】
 *   （支持目前绝大多数语言，例如C++,C#,JAVA.PYTHON 等）
 *
 */


      NioEventLoopGroup bossGroup = new NioEventLoopGroup();
      NioEventLoopGroup workerGroup = new NioEventLoopGroup();
    try {
      //创建服务器端的启动对象，配置参数
      ServerBootstrap bootstrap = new ServerBootstrap();

      bootstrap.group(bossGroup, workerGroup)//设置两个线程组
          //Channel的定义是跟我们的协议有关系的
          /**
           * NioSocketChannel异步的客户端TCP Socket连接
           * NioServerSocketChannel 异步的服务器端TCP Socket连接
           * NioDatagramChannel 异步的UDP连接
           * NioSctpServerChannel  异步的sctp服务器端连接 ，这些通道涵盖了UDP和TCP网络IO以及文件IO
           */
          .channel(NioServerSocketChannel.class)//使用NioSocketChannel作为服务器通道实现
          .option(ChannelOption.SO_BACKLOG, 128)//设置线程队列得到的连接个数
          .childOption(ChannelOption.SO_KEEPALIVE, true)//设置保持活动连接状态
          .childHandler(new ChannelInitializer<SocketChannel>() {//创建一个通道测试对象（匿名对象）


            //给pipeline  设置处理器
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
              ChannelPipeline pipeline = socketChannel.pipeline();
              /**
               * pipeline 加入解码器
               * 传入参数，要解码的对象
               */
              pipeline.addLast(new ProtobufDecoder(MyDataInfo.MyMessage.getDefaultInstance()));
              pipeline.addLast(new NettyServerHandler());
            }
          });//给我们的workerGroup的EventLoop对应的管道设置处理器

      System.out.println(".....服务器 is  ready ...");
      //绑定一个端口，并且同步处理，生成一个ChannelFuture对象
      ChannelFuture channelFuture = bootstrap.bind(6668).sync();
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
    }finally {
      bossGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();
    }
  }




}
