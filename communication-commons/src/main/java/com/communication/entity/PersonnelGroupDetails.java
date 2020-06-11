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
@ApiModel(value = "人员分组详情表",description = "人员分组详情表")
public class PersonnelGroupDetails {

  @ApiModelProperty(value = "人员分组详情主键")
  private String pgdId;

  @ApiModelProperty(value = "人员分组主键")
  private String pgId;

  @ApiModelProperty(value = "人员Id")
  private Long personnelId;

  @ApiModelProperty(value = "描述")
  private String description;

  @ApiModelProperty(value = "创建时间")
  private Date createBy;
}
