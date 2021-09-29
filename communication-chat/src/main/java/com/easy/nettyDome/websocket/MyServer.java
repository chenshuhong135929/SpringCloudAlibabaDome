package com.easy.nettyDome.websocket;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;


/**
 * @Auther ChenShuHong
 * @Date 2021-09-28 16:21
 */
public class MyServer {


    public static void main(String[] args) throws InterruptedException {
      NioEventLoopGroup bossGroup = new NioEventLoopGroup();
      NioEventLoopGroup workerGroup = new NioEventLoopGroup();

      try {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup,workerGroup)
            .channel(NioServerSocketChannel.class)
            //添加一个boss日志处理器
            .handler(new LoggingHandler(LogLevel.INFO))
            .childHandler(new ChannelInitializer<SocketChannel>() {
              @Override
              protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                /**
                 * 因为基于http协议，使用http的编码解码器
                 */

                pipeline.addLast(new HttpServerCodec());
                //是以块方式写，添加ChunkedWriteHandler处理器
                pipeline.addLast(new ChunkedWriteHandler());
                /**
                 * 说明：
                 * 1，http数据在传输过程中是分段，HttpObjectAggregator ,就是可以将多个段聚合
                 * 2，这就是为什么，当浏览器发送大量数据时，就会发出多次http请求
                 */
                pipeline.addLast(new HttpObjectAggregator(8192));
                /**
                 * 说明：
                 * 1，对于webSocket,它的数据是以帧（frame）形式传递
                 * 2，可以看到webSocketFrame下面有六个子类
                 * 3，浏览器请求时 ws://localhost:7000/xxx表示请求的url
                 * 4，WebSocketServerProtocolHandler核心功能是将http协议升级为ws协议，保持长连接
                 *
                 */
                pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));
                //自定义的handler处理业务逻辑
                pipeline.addLast(new MyTextWebSocketFrameHandler());
              }
            });

        ChannelFuture channelFuture = bootstrap.bind(8080).sync();
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

