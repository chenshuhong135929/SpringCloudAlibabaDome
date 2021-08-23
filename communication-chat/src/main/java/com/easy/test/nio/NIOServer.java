package com.easy.test.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @Auther ChenShuHong
 * @Date 2021-08-19 17:43
 */
public class NIOServer {

  public static void main(String[] args) throws IOException {

    //得到一个selector对象
    Selector selector = Selector.open();
    //创建ServerSocketChannel对象
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    //绑定一个端口6666，在服务器端监听
    serverSocketChannel.socket().bind(new InetSocketAddress(9999));
    //设置为非阻塞
    serverSocketChannel.configureBlocking(false);
    //把serverSocketChannel 注册到selector 关心事件为OP_ACCEPT
    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    //循环等待客户端连接
    while (true){
      if(selector.select(1000)==0){//没有事件发生
        System.out.println("等待服务器1秒，无连接");
        continue;

      }

      //如果返回的>0，就获取到相关的selectionKey集合
      //1，如果返回的>0，表示已经获取到关注的事件
      //2，selector.selectedKeys()返回关注事件的集合
      // 通过selectionKeys 反向获取通道
      Set<SelectionKey> selectionKeys = selector.selectedKeys();
      Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
      while (keyIterator.hasNext()){

        //获取到selectionKey
        SelectionKey key = keyIterator.next();
        //根据key对应的通道发生的时间做相对应的处理
        if(key.isAcceptable()){

          //该客户端生成一个SocketChannel
          SocketChannel socketChannel = serverSocketChannel.accept();
          System.out.println("客户端连接成功了 生成了一个socketChannel "+socketChannel.hashCode());
          //将socketChannel设置为非阻塞
          socketChannel.configureBlocking(false);
          //将socketChannel 注册到selector,关注事件为OP_READ，同时给socketChannel
          //关联一个Buffer
          socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
          System.out.println("客户端注册到selectionkey 数量="+  selector.keys().size());
        }

        //
        if(key.isReadable()){//发生OP_READ
          //通过key 方向获取到对应的channel
          SocketChannel channel =(SocketChannel) key.channel();
          //获取到该channel 关联的buffer
          ByteBuffer buffer = (ByteBuffer)key.attachment();
          channel.read(buffer);
          System.out.println("form 客户端"+ new String(buffer.array()));

        }


        //手动从集合中移除当前的selectionKey,防止重复操作
        keyIterator.remove();


      }


    }
  }


}
