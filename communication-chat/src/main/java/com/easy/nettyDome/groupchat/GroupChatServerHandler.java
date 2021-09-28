package com.easy.nettyDome.groupchat;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @Auther ChenShuHong
 * @Date 2021-09-27 15:43
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String > {


  //定义一个Channle组  ，管理所偶的channel
  //GlobalEventExecutor.INSTANCE 是全局事件的执行器，是一个单例
  private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


  /**
   * 链接建立，一旦连接第一个被执行
   * 将当前这个channel加到ChannelGroup中
   * @param ctx
   * @throws Exception
   */
  @Override
  public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
    Channel channel = ctx.channel();
    /**
     * 将客户端加入论坛的信息推送给其他客户端
     * channelGroup.writeAndFlush 所有的channel遍历一遍并发送
     */
    channelGroup.writeAndFlush("【客户端】"+channel.remoteAddress()+"加入聊天\n");
    channelGroup.add(channel);

  }

  /**
   * 断开连接，将xx客户离线推送给在线用户
   * @param ctx
   * @throws Exception
   */
  @Override
  public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

    Channel channel = ctx.channel();
    channelGroup.writeAndFlush(Unpooled.copiedBuffer("【客户端】"+channel.remoteAddress()+"离开了\n",CharsetUtil.UTF_8));
    //不需要的removed channelGroup  触发handlerRemoved会自己去移除当前channel
  }

  /**
   * 表示channel处于活动状态 提示上线
   * @param ctx
   * @throws Exception
   */
  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {

    System.out.println(Unpooled.copiedBuffer(ctx.channel().remoteAddress()+"上线了~",CharsetUtil.UTF_8));

  }

  /**
   * 表示channel 处于不在活动状态，提示xx离线
   * @param ctx
   * @throws Exception
   */
  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {

    System.out.println(Unpooled.copiedBuffer(ctx.channel().remoteAddress()+"离线了~",CharsetUtil.UTF_8));
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
    Channel channel = ctx.channel();
    //这时我们遍历channelGroup,根据不同情况，回送不同的消息
    channelGroup.forEach(ch->{

      if(channel !=ch){
        //不是当前的channel直接转发
        ch.writeAndFlush(Unpooled.copiedBuffer("【客户】"+ channel.remoteAddress() +"发送消息： "+msg+"\n", CharsetUtil.UTF_8));
      }

    });

  }

  /**
   * 发生异常关闭通道
   * @param ctx
   * @param cause
   * @throws Exception
   */
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

    ctx.close();
  }
}
