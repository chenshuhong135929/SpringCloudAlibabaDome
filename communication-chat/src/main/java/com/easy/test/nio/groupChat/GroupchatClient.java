package com.easy.test.nio.groupChat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @Auther ChenShuHong
 * @Date 2021-08-24 15:16
 */
public class GroupchatClient {
  private  final String HOST ="127.0.0.1";
  private  final int PORT =6666;
  private  Selector selector;
  private SocketChannel socketChannel;
  private  String username;

  public GroupchatClient() throws IOException {
    selector= Selector.open();
    socketChannel = socketChannel.open(new InetSocketAddress(HOST,PORT));
    socketChannel.configureBlocking(false);
    socketChannel.register(selector, SelectionKey.OP_READ);
    username= socketChannel.getLocalAddress().toString().substring(1);
    System.out.println(username+"is ok...");
  }

  //向服务器发送消息
  public void sendInfo(String info){
    info=username+" 说：  "+info;
    try {
      socketChannel.write(ByteBuffer.wrap(info.getBytes()));
    }catch (IOException e){
      e.printStackTrace();
    }
  }

  //读取从服务器端回复消息
  public void readInfo(){
    try {
      int redaChanels = selector.select();
      if(redaChanels>0){//有可以用的通道
        Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
        while (iterator.hasNext()){
          SelectionKey key = iterator.next();
          if(key.isReadable()){
              //得到相关通道
            SocketChannel sc = (SocketChannel) key.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            sc.read(byteBuffer);
            String msg = new String(byteBuffer.array());
            System.out.println(msg.trim());

          }
        }
        iterator.remove();
      }else {
      //  System.out.println("没有可用的通道");
      }
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws IOException {
    GroupchatClient  chatClient = new GroupchatClient();
    //启动一个线程工作

    new Thread(()->{
        while (true){
          chatClient.readInfo();
          try {
            Thread.currentThread().sleep(3000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
    }).start();


    //发送数据给服务器端
    Scanner scanner = new Scanner(System.in);
    while (scanner.hasNextLine()){
      String s = scanner.nextLine();
      chatClient.sendInfo(s);
    }

  }
}
