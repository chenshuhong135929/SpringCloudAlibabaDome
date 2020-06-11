package com.easy.service.impl;

import cn.hutool.core.util.IdUtil;
import com.communication.entity.PersonnelChat;
import com.communication.entity.PersonnelChatTemporary;
import com.communication.entity.PersonnelGroup;
import com.communication.entity.PersonnelGroupDetails;
import com.easy.constant.FeturnErrorCodeConstant;
import com.easy.dao.SendDao;
import com.easy.nettyServer.ClientMap;
import com.easy.service.SendService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SendServiceImpl implements SendService {

  @Resource
  SendDao sendDao;



  @Override
  public  void login(ChannelHandlerContext ctx, String sender) {
    if(ClientMap.getClien(sender)==null){
      ClientMap.addClien(sender,ctx.channel());
    }
  }

  @Transactional
  @Override
  public  void oneSend(ChannelHandlerContext ctx, Object msg) {
    String[] msgtext = msg.toString().split("#");
    String sender = msgtext[1];
    String receiver = msgtext[2];
    Channel receiverChannel = ClientMap.getClien(receiver);
    //用户没有在线需要保存到临时表里
    if(receiverChannel==null){
      PersonnelChatTemporary  personnelChatTemporary = new PersonnelChatTemporary();
      personnelChatTemporary.setChatData(msg.toString());
      personnelChatTemporary.setCId(IdUtil.simpleUUID());
      personnelChatTemporary.setCreateBy(new Date());
      personnelChatTemporary.setPgId("0");
      personnelChatTemporary.setPersonnelId(Long.parseLong(receiver));
      sendDao.addPersonnelChatTemporary(personnelChatTemporary);
      return;
    }
    //保存聊天信息
    PersonnelChat  personnelChat = new PersonnelChat();
    personnelChat.setChatData(msg.toString());
    personnelChat.setCId(IdUtil.simpleUUID());
    personnelChat.setCreateBy(new Date());
    personnelChat.setPersonnelId(Long.parseLong(receiver));
    personnelChat.setPgId("0");
    sendDao.addPersonnelChat(personnelChat);
    receiverChannel.writeAndFlush(msg.toString());
  }

  @Transactional
  @Override
  public void groupSend(ChannelHandlerContext ctx, Object msg) {
    String[] msgtext = msg.toString().split("#");
    String sender = msgtext[1];
    String groupId = msgtext[2];
    PersonnelGroup byGroupId = sendDao.findByGroupId(groupId);
    if(byGroupId==null){
      ctx.writeAndFlush(FeturnErrorCodeConstant.Error_401.getKey());
      return;
    }
    List<PersonnelGroupDetails> byGroupIdDetails = sendDao.findByGroupIdDetails(groupId);
    for(PersonnelGroupDetails p:  byGroupIdDetails){
      //排除自己本身
      if(sender.equals(String.valueOf(p.getPersonnelId()))){
        continue;
      }
      Channel receiverChannel = ClientMap.getClien(String.valueOf(p.getPersonnelId()));
      //用户没有在线需要保存到临时表里
      if(receiverChannel==null){
        PersonnelChatTemporary  personnelChatTemporary = new PersonnelChatTemporary();
        personnelChatTemporary.setChatData(msg.toString());
        personnelChatTemporary.setCId(IdUtil.simpleUUID());
        personnelChatTemporary.setCreateBy(new Date());
        personnelChatTemporary.setPersonnelId(p.getPersonnelId());
        personnelChatTemporary.setPgId(p.getPgdId());
        sendDao.addPersonnelChatTemporary(personnelChatTemporary);
        continue;
      }
      //保存聊天信息
      PersonnelChat  personnelChat = new PersonnelChat();
      personnelChat.setChatData(msg.toString());
      personnelChat.setCId(IdUtil.simpleUUID());
      personnelChat.setCreateBy(new Date());
      personnelChat.setPersonnelId(p.getPersonnelId());
      personnelChat.setPgId(p.getPgId());
      sendDao.addPersonnelChat(personnelChat);
      receiverChannel.writeAndFlush(msg);

    }

  }

//  @Transactional
//  @Scheduled(cron = "*/5 * * * * ?")
//  public void s1()  {
//    List<PersonnelChatTemporary> personnelChatTemporaries = sendDao.finbyAll();
//    for(PersonnelChatTemporary p: personnelChatTemporaries ){
//      Channel receiverChannel = ClientMap.getClien(String.valueOf(p.getPersonnelId()));
//      if(receiverChannel !=null){
//        //保存聊天信息
//        PersonnelChat  personnelChat = new PersonnelChat();
//        personnelChat.setChatData(p.getChatData());
//        personnelChat.setCId(IdUtil.simpleUUID());
//        personnelChat.setCreateBy(p.getCreateBy());
//        personnelChat.setPersonnelId(p.getPersonnelId());
//        personnelChat.setPgId(p.getPgId());
//        sendDao.addPersonnelChat(personnelChat);
//        sendDao.deletePersonnelChatTemporary(p.getCId());
//        receiverChannel.writeAndFlush(p.getChatData());
//      }
//
//
//    }
//  }
}
