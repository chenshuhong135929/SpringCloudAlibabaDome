package com.easy.nettyDome.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @Auther ChenShuHong
 * @Date 2021-09-26 17:01
 */
public class NettyByteBuf01 {
  public static void main(String[] args) {
    /**
     * 创建一个ByteBuf
     * 说明
     * 1，创建对象，该对象包含一个数组arr,是一个byte[10]
     * 2，在netty的buffer中，不需要使用flip进行反转
     * 3，通过readerIndex和 writerIndex和capacity，将buffer分成三个区域
     *
     */
    ByteBuf byteBuf = Unpooled.buffer(10);
    for(int i = 0 ; i <10; i++){
        byteBuf.writeByte(i);
    }

    System.out.println("capacity = "+byteBuf.capacity());//10
    for(int i = 0 ; i <byteBuf.capacity(); i++){
       System.out.println(byteBuf.getByte(i));

    }

    for(int i = 0 ; i <byteBuf.capacity(); i++){
      System.out.println(byteBuf.readByte());

    }
  }
}
