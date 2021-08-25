package com.easy.test.nio;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @Auther ChenShuHong
 * @Date 2021-07-20 11:19
 * 在io中操作Channel
 */
public class NIOFileChannel {
  public static void main(String[] args) throws IOException {

    test7();
    return;
  }

  private static void OutputChannel() throws IOException {
    String str = "hello,chenshuhong";
    //创建一个输出流-> channel
    FileOutputStream fileOutputStream = new FileOutputStream("E:\\rr.txt");
    FileChannel channel = fileOutputStream.getChannel();
    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
    byteBuffer.put(str.getBytes());
    //buffer需要进行反转才能写入
    byteBuffer.flip();
    channel.write(byteBuffer);
    fileOutputStream.close();
  }

  public static  void  InputChannel() throws IOException {
    FileInputStream fileInputStream = new FileInputStream("E:\\rr.txt");
    FileChannel channel = fileInputStream.getChannel();
    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
    channel.read(byteBuffer);
    System.out.println(new String (byteBuffer.array()));
    fileInputStream.close();

  }


  public static void test3() throws IOException {
    FileInputStream fileInputStream = new FileInputStream("E:\\rr.txt");
    FileOutputStream fileOutputStream = new FileOutputStream("E:\\rr1.txt");
    FileChannel channel = fileInputStream.getChannel();
    FileChannel channel1 = fileOutputStream.getChannel();

    ByteBuffer byteBuffer =ByteBuffer.allocate(1024);

    while(true){
      byteBuffer.clear();
      int read = channel.read(byteBuffer);
      if(read==-1){
        break;
      }
      byteBuffer.flip();
      channel1.write(byteBuffer);

    }

    fileInputStream.close();
    fileOutputStream.close();
  }

  /**
   * 使用tarnsferFrom来进行通道copy
   *
   * @throws IOException
   */
  public static  void test4()throws IOException {
    FileInputStream fileInputStream = new FileInputStream("E:\\rr.txt");
    FileOutputStream fileOutputStream = new FileOutputStream("E:\\rr1.txt");
    FileChannel channel = fileInputStream.getChannel();
    FileChannel channel1 = fileOutputStream.getChannel();
    channel1.transferFrom(channel,0,channel.size());
    fileInputStream.close();
    fileOutputStream.close();
  }

  /**
   * buffer存入的类型跟取出的类型要一致
   */
  public static  void test5(){
    ByteBuffer byteBuffer  = ByteBuffer.allocate(1024);
    byteBuffer.putInt(3);
    byteBuffer.flip();
    System.out.println(byteBuffer.getInt());
    while (byteBuffer.hasRemaining()){
      System.out.println(byteBuffer.get());
    }

  }

  /**
   * MappedByeBuffer （文件可以直接在内存修改，性能会提升） 可让文件直接在内存（堆外内存）修改，操作系统不需要拷贝一次
   * @throws FileNotFoundException
   */
  public static void test6() throws IOException {
    RandomAccessFile randomAccessFile = new RandomAccessFile("1.txt","rw");
    FileChannel channel = randomAccessFile.getChannel();
    /**
     * 参数1：FileChannel.MapMode.REA_WRITE使用读写模式
     * 参数2：0; 可以直接修改起始位置
     * 参数3：  5：  是影视到内存的大小，即将  1.txt的多是个字节映射到内存
     * 可以直接修改的范围是0-5 (字节)
     */
    MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
    mappedByteBuffer.put(0,(byte)'H');
    randomAccessFile.close();
  }

  /**
   * scattering :将数据写入到buffer时，可以采用buffer数组，依次写入【分散】
   * Gathering :从buffer读取数据时，可以采用buffer依次读
   * 如果数据量大可以用多个buffer[]数组进行分批处理，更快
   */
  public static void test7() throws IOException {
      //使用ServerSocketChannel 和socketchannel网络
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
    serverSocketChannel.socket().bind(inetSocketAddress);
    ByteBuffer [] byteBuffers =new ByteBuffer[2];
    byteBuffers[0]=ByteBuffer.allocate(5);
    byteBuffers[1]=ByteBuffer.allocate(3);
    SocketChannel socketChannel = serverSocketChannel.accept();
    int messageLengght =8;//假定从客户端接收8个字节
    //循环的读取
    while (true){
      int  byteRead =0;
      while (byteRead<messageLengght){
        long l = socketChannel.read(byteBuffers);
        byteRead +=l;//累计读取的字节数
        System.out.println("byteRead="+byteRead);
        //输出流打印，看看当前的這个buffer的position ,limit
        Arrays.asList(byteBuffers).stream().map(byteBuffer -> "position"+byteBuffer.position()+"limit"+byteBuffer.limit()).forEach(System.out::println);
      }

      //将所有的buffer进行一个flip
      Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.flip());
      //将我们的数据读出显示到客户端
      long byteWirte=0;
      while (byteWirte<messageLengght){
        long l = socketChannel.write(byteBuffers);
        byteWirte+=l;
        //复位
        Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.clear());

      }
    }


  }


  /**
   * 文件零拷贝
   */
  public void test9() throws IOException {
    SocketChannel socketChannel = SocketChannel.open();
    socketChannel.socket().bind(new InetSocketAddress("localhost",999));
    socketChannel.configureBlocking(false);
    //FileChannel fileChannel = FileChannel.open()
    FileChannel channel = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\springcloud-learning-master.zip")).getChannel();
   //transferTo一次只能上传8m所以后面可以做一个循环进行操作
    long transferCount = channel.transferTo(0, channel.size(), socketChannel);
    System.out.println("发送的总字节数： "+transferCount);
    channel.close();

  }

  /**
   * 1，NIO中的ServerSocketChannel功能似ServerSocket ,SocketChannel 功能类似Socket
   * 2，selector 相关方法说明
   * selector.select() 阻塞
   * selector select(1000)阻塞1000毫秒，在1000毫秒返回
   * select wakeup() 唤醒selector
   * selector.selectNow() 不阻塞，立马返回
   */
}
