package com.easy.nettyDome.inboundhandlerandoutboundhardler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @Auther ChenShuHong
 * @Date 2021-10-08 10:05
 */
public class MyByteToLongDecoder extends ByteToMessageDecoder {
  /**
   *  decode 会根据接收到的数据，被调用多次，直到确定没有新的元素被添加到list
   *  或者是ByteBuf没有更多的可读字节为止
   *  如果list out 不为空就会将list的内容传递到下一个channelinboundhandler,该方法会被调用多次
   * @param ctx  上下文
   * @param in   入站的ByteBuf
   * @param out  List集合，将解码后的数据传给下一个handler
   * @throws Exception
   */
  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

    //因为Long8个字节
    if(in.readableBytes()>=8){
      out.add(in.readLong());
    }
  }
}
