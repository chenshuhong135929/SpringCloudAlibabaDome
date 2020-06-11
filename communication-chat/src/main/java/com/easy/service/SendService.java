package com.easy.service;

import io.netty.channel.ChannelHandlerContext;

public interface SendService {
  /**
   * 登陆
   * @param ctx
   * @param sender
   */
  void login(ChannelHandlerContext ctx, String sender);

  /**
   * 单点发送
   * @param ctx
   * @param msg
   */
  void oneSend(ChannelHandlerContext ctx, Object msg);

  /**
   * 分组发送消息
   * @param ctx
   * @param msg
   */
  void groupSend(ChannelHandlerContext ctx, Object msg);
}
