package com.easy.test.nio.groupChat;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @Auther ChenShuHong
 * @Date 2021-08-23 17:30
 */
public class GroupChatServer {

  //定义属性
  private Selector selector;
  private ServerSocketChannel listenChannel;
  private static  final int PORT =6666;

  public GroupChatServer() {
  try {
      selector =Selector.open();
      listenChannel= listenChannel.open();
      //定义监听的端口
      listenChannel.socket().bind(new InetSocketAddress(PORT));
      //非阻塞
      listenChannel.configureBlocking(false);
      //将该listenChannel 注册到selector(OP_ACCEPT有新客户链接的话就把它加到selector中)
      listenChannel.register(selector, SelectionKey.OP_ACCEPT);
  }catch (IOException  e){
    e.printStackTrace();
  }
  }

  //监听
  public void listen(){
    try {
      while (true){
        int count = selector.select();
        //大于0有事件要处理
        if(count>0){
          Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
          while (iterator.hasNext()){
            SelectionKey key = iterator.next();
            //监听到的是accept（有新设备连接上来，就注册到selector里，接听读取事件）
            if(key.isAcceptable()){
              SocketChannel sc = listenChannel.accept();
              sc.configureBlocking(false);
              //将该sc政策到selector上
              sc.register(selector,SelectionKey.OP_READ);
              System.out.println(sc.getLocalAddress()+"上线了");
            }

            //监听读取事件
            if(key.isReadable()){
              readData(key);

            }
            //将当前的key删除防止重复处理
            iterator.remove();
          }
        }else {
          System.out.println("等待");
        }
      }
    }catch (IOException e){
      e.printStackTrace();
    }finally {

    }
  }


  //读取客户端消息
  private  void readData(SelectionKey key){
    SocketChannel channel=null;
    try {
       channel = (SocketChannel) key.channel();
      ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
      int count  = channel.read(byteBuffer);
      //根据count的值做处理
      if(count>0){
        String msg = new String(byteBuffer.array());
        System.out.println(channel.getLocalAddress()+"form 客户端"+  msg);
        //向其它客户端发送消息
        sendInfoToOtherClients(msg,channel);
      }
    }catch (IOException  e){
      try {
        System.out.println(channel.getRemoteAddress()+"离线了");
        //取消注册，
        key.cancel();
        //关闭通道
        channel.close();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
      e.printStackTrace();
    }
  }

  //转发消息给其他客户端
  private  void sendInfoToOtherClients(String msg ,SocketChannel self) throws IOException {
    System.out.println("服务器转发消息中。。。");
    //遍历所有注册到selector上的socketChannel ,并排除self
   for(SelectionKey key : selector.keys()){
     //通过key  获取到对应的socketChannel
      Channel targetChanel =   key.channel();
     if(targetChanel instanceof SocketChannel && targetChanel!=self ){
       ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
       SocketChannel dest=(SocketChannel) targetChanel;
       //将数据写入到buffer中
       dest.write(byteBuffer);
     }
   }


  }

  public static void main(String[] args) {
    GroupChatServer groupChatServer = new GroupChatServer();
    groupChatServer.listen();
  }

}
