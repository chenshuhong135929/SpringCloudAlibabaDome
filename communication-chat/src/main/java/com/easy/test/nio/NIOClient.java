package com.easy.test.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Auther ChenShuHong
 * @Date 2021-08-20 15:30
 */
public class NIOClient {
  public static void main(String[] args) throws IOException {
    SocketChannel socketChannel = SocketChannel.open();
    socketChannel.configureBlocking(false);
    InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", 9999);
    //连接服务器
    if(!socketChannel.connect(inetSocketAddress)){

      while (!socketChannel.finishConnect()){
        System.out.println("因为连接需要时间，客户端不会阻塞，可以走其他工作");
      }

    }
    //。。。如果连接成功，就发送数据
    String str ="hello ";
    ByteBuffer wrap = ByteBuffer.wrap(str.getBytes());
    //发送数据，将buffer数据写到channel里面去
    socketChannel.write(wrap);
    System.in.read();
  }
}
