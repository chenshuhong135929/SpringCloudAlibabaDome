package com.communication.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "聊天用户离线临时表",description = "聊天用户离线临时表")
public class PersonnelChatTemporary {

  @ApiModelProperty(value = "聊天主键")
  private String cId;

  @ApiModelProperty(value = "人员分组主键")
  private String pgId;

  @ApiModelProperty(value = "人员Id")
  private Long personnelId;

  @ApiModelProperty(value = "聊天内容")
  private String chatData;

  @ApiModelProperty(value = "创建时间")
  private Date createBy;

}
