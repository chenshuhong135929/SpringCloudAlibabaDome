package com.easy.nettyDome.inboundhandlerandoutboundhardler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @Auther ChenShuHong
 * @Date 2021-10-08 15:22
 */
public class MyByteToLongDecoder2 extends ReplayingDecoder<Void> {
  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
    System.out.println("MyByteToLongDecoder2被调用");
    //在ReplayingDecoder 不需要判断数据是否足够读取，内部会进行处理判断
    out.add(in.readLong());
  }
}
