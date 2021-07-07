package com.wisdomcommand.communication.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Auther ChenShuHong
 * @Date 2020-06-03 13:43
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "授权用户 ",description = "授权用户 表")
public class User implements Serializable {


  @ApiModelProperty(value = "产品主键")
  @TableId(type = IdType.AUTO)
  private long id;

  @ApiModelProperty(value = "密码")
  private String passWord;

  @ApiModelProperty(value = "名称")
  private String userName;

  @ApiModelProperty(value = "角色，USER，ADMIN，")
  private String  role;
}
