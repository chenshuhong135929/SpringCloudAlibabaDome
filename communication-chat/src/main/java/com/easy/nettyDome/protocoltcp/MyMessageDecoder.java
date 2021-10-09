package com.easy.nettyDome.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @Auther ChenShuHong
 * @Date 2021-10-09 10:42
 */
public class MyMessageDecoder extends ReplayingDecoder<Void> {
  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
    System.out.println("MyMessageDecoder被调用");
    //在这里需要将我们的MessageProtocol数据包转换成对象
    int i = in.readInt();
    byte [] buff = new byte[i];
    in.readBytes(buff);
    MessageProtocol messageProtocol=new MessageProtocol();
    messageProtocol.setLen(i);
    messageProtocol.setContent(buff);
    out.add(messageProtocol);
  }
}
