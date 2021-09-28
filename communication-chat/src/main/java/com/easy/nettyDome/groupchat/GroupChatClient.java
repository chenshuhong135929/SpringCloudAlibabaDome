package com.easy.nettyDome.groupchat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * @Auther ChenShuHong
 * @Date 2021-09-27 16:53
 */
public class GroupChatClient {
  private final String host ;
  private final  int port;

  public GroupChatClient(String host, int port  ){
        this.host= host;
        this.port=port;
  }

  public void run () throws InterruptedException {

    NioEventLoopGroup group  = new NioEventLoopGroup();

    try {
      Bootstrap bootstrap = new Bootstrap();
      bootstrap.group(group)
          .channel(NioSocketChannel.class)
          .handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
              ChannelPipeline pipeline = ch.pipeline();
              //加入解码器
              pipeline.addLast("decoder",new StringDecoder());
              //编码器
              pipeline.addLast("encoder",new StringEncoder());
              //业务类
              pipeline.addLast(new GroupChatClientHandler());
            }
          });
      ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
      if(channelFuture.isSuccess()){
        System.out.println("链接服务端成功");
        Channel channel = channelFuture.channel();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
          String msg = scanner.nextLine();
          //将接收到的信息发送出去
          channel.writeAndFlush(msg);
        }
      }

      channelFuture.channel().closeFuture().sync();
    }finally {
      group.shutdownGracefully();
    }


  }

  public static void main(String[] args) throws InterruptedException {
    new GroupChatClient("localhost",6666).run();
  }
}
