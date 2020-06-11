package com.easy.dao;

import com.communication.entity.PersonnelChat;
import com.communication.entity.PersonnelChatTemporary;
import com.communication.entity.PersonnelGroup;
import com.communication.entity.PersonnelGroupDetails;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SendDao {
  /**
   * 添加离线信息
   * @param personnelChatTemporary
   */
  void addPersonnelChatTemporary(PersonnelChatTemporary personnelChatTemporary);

  /**
   * 添加聊天信息
   * @param personnelChat
   */
  void addPersonnelChat(PersonnelChat personnelChat);

  /**
   * 通过分组ID获取分组数据
   * @param pgid
   * @return
   */
  PersonnelGroup findByGroupId(String pgid);

  /**
   * 获取分组信息详情人员
   * @param pgId
   * @return
   */
  List<PersonnelGroupDetails >findByGroupIdDetails(String pgId);

  /**
   * 获取所有离线消息
   * @return
   */
  List<PersonnelChatTemporary>finbyAll();

  /**
   * 删除离线消息
   * @param cid
   * @return
   */
  Integer deletePersonnelChatTemporary(String cid);

}
