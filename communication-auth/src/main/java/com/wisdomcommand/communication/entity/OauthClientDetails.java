package com.wisdomcommand.communication.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther ChenShuHong
 * @Date 2020-11-27 15:21
 */
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "授权客户端资源 ",description = "授权客户端资源 表")
@Data
public class OauthClientDetails {

  @ApiModelProperty(value = "客户端Id")
  @TableId(type = IdType.INPUT)
  private String clientId;

  @ApiModelProperty(value = "资源Id")
  private String resourceIds;

  @ApiModelProperty(value = "客户端秘钥")
  private String clientSecret;

  @ApiModelProperty(value = "授权范围")
  private String scope;

  @ApiModelProperty(value = "授权类型（implicit,client_credentials,authorization_code,refresh_token,password）")
  private String authorizedGrantTypes;

  @ApiModelProperty(value = "授权的web地址")
  private String webServerRedirectUri;

  @ApiModelProperty(value = "授权角色")
  private String authorities;

  @ApiModelProperty(value = "token有效时间")
  private long accessTokenValidity;

  @ApiModelProperty(value = "token刷新时间")
  private long refreshTokenValidity;

  @ApiModelProperty(value = "附加信息")
  private String additionalInformation;

  @ApiModelProperty(value = "自动批准")
  private String autoapprove;

}
