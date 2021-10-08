package com.easy.nettyDome.inboundhandlerandoutboundhardler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Auther ChenShuHong
 * @Date 2021-10-08 10:18
 *
 * Netty的handler链的调用机制
 *
 * 结论：
 *  不论解码器handler还是编码器handler既接收的消息类型必须与待处理的类型一致否则该handler不会被执行
 *  在解码器进行数据解码时，需要判断缓存区（ByteBuff）的数据是否足够，否则接收到的结果会和期望结果可能不一致
 *
 *  netty有许多自带的解码器
 *  1，LineBasedFrameDecoder:使用尾控制字符（\n或者\r\n）
 *  2，DelimiterBasedFrameDecoder : 使用自定义的特殊字符作为消息的分隔符
 *  3，HttpObjectDecoder:一个HTTP数据的解码器
 *  4，LengthFileIdBasedFrameDecoder:通过指定长度来标识整包消息，這样就可以自动的处理粘包和半包消息
 *
 */
public class MyServer {
  public static void main(String[] args) throws InterruptedException {
    NioEventLoopGroup bossGroup = new NioEventLoopGroup();
    NioEventLoopGroup workerGroup = new NioEventLoopGroup();

    try{

      ServerBootstrap bootstrap = new ServerBootstrap();
      bootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class).childHandler (new MyServerInitializer());
      //绑定一个端口，并且同步处理，生成一个ChannelFuture对象
      ChannelFuture channelFuture = bootstrap.bind(8080).sync();
      //给ChannelFuture 注册监听器，监控我们关心的事件
      channelFuture.addListener(
          (ChannelFutureListener) future -> {
            if(channelFuture.isSuccess()){
              System.out.println("监听端口  8080  成功");
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
