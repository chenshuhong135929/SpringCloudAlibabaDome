package com.easy.nettyDome.codec;

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
     * 1，Netty抽象出两组线程池BossGroup专门负责接收客户端的连接，WorkeGroup专门负责网络读写
     * 2，BossGroup和WorkerGroup类型都是hiNioEventLoopGroup
     * 3,NioEventLoopGroup相当于一个事件循环组，这个组中包含有多个事件循环，每一个事件循环是NioEventLoop
     * 4,NioEventLoop表示一个不断循环的执行处理任务的线程，每个NioEventLoop都有一个selector，用于监听绑定在其上的
     * socket的网络通讯
     * 5，NioEventLoopGroup 可以是多个线程，即可以含有NioEventLoop
     * 6，每个Boss NioEventLoop执行的步骤有3步
     * 	1，轮询accept事件
     * 	2，处理accept事件，与client建立连接，生成NioScoketChannel,并将其注册到某个worker NioEventLoop上的selector
     * 	3，处理任务队列的任务，即runAllTasks
     * 7，每个WorkerNioEventLoop循环执行的步骤
     * 	1，轮询read ，write事件
     * 	2，处理I/O事件，即read,write事件，在应对NioScoketChannel处理
     * 	3，处理任务队列的任务，即runAllTasks
     * 8，每个Worker NioEventLoop 处理业务时，会使用pipeline(管道)，pipeline 中包含了channel，即通过pipeline，可以获取到对应通道，管理中维护很多的处理器
     *
     *  创建BossGroup 和WorkerGroup
     *  说明
     *  1，创建两个线程组bossGroup和workerGroup
     *  2，bossGroup只是处理连接请求，真正的客户端业务处理，会交给workerGroup完成
     *  3，两个都是无限循环
     *  4，bossGroup 和 workerGroup 含有的子线程（NioEventLoop）的个数
     *  默认实际cpu核数 * 2
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
              pipeline.addLast(new ProtobufDecoder(StudentPOJO.Student.getDefaultInstance()));
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
