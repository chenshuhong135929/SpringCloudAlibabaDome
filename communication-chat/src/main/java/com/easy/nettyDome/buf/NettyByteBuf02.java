package com.easy.nettyDome.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/**
 * @Auther ChenShuHong
 * @Date 2021-09-26 17:24
 */
public class NettyByteBuf02 {
  public static void main(String[] args) {
    /**
     * 创建ByteBuf
     *
     */
    ByteBuf byteBuf = Unpooled.copiedBuffer("Hello,wold!", CharsetUtil.UTF_8);
    /**
     * 使用相关的apl方法
     *
     */
    if(byteBuf.hasArray()){//true
      byte[] content = byteBuf.array();
      //将content转为字符串
      System.out.println(new String(content,CharsetUtil.UTF_8));

      System.out.println("byteBuff=="+ byteBuf);
      System.out.println(byteBuf.arrayOffset());
      System.out.println(byteBuf.readerIndex());
      System.out.println(byteBuf.writerIndex());
      System.out.println(byteBuf.capacity());
      int i = byteBuf.readableBytes();//可读的字节数 12
    }
  }
}
