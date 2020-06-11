package com.easy.nettyServer;

import com.easy.constant.ClientConstant;
import com.easy.service.SendService;
import com.easy.utils.SpringUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {


    private static SendService sendService;
    static {
        sendService = SpringUtil.getBean(SendService.class);
    }
    /**
     * 客户端连接会触发
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("Channel active......");
    }


    /**
     * 客户端发消息会触发
     * (A 发送消息)     A#发送者#接收者#信息     返回   NULL     接收者  A#发送者#接收者#信息
     * (B 为登录)       B#登录者                 返回   NULL
     * (C 群发送消息)   C#发送者#分组#信息       返回   NULL     接收者  C#发送者#分组#信息
     */
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("服务器收到消息: {}", msg.toString());
        String[] msgtext = msg.toString().split("#");
        String requestType = msgtext[0];

        sendService.login(ctx, msgtext[1]);

        //单点发送
        if(requestType.equals(ClientConstant.A.getKey())){
            sendService.oneSend(ctx, msg);
        }

        //分组发送
        if(requestType.equals(ClientConstant.C.getKey())){
            sendService.groupSend(ctx, msg);
        }
    }


    /**
     * 发生异常触发
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ClientMap.removeClien(ctx.channel());
        ctx.close();
    }


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        ClientMap.removeClien(ctx.channel());
        ctx.close();
    }












}
