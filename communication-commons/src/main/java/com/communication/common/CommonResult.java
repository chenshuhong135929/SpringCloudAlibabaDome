package com.communication.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "返回封装类",description = "返回封装类")
public class CommonResult<T>
{
  @ApiModelProperty(value = "状态码")
  private Integer code;
  @ApiModelProperty(value = "提示信息")
  private String  message;
  @ApiModelProperty(value = "数据")
  private T       data;

  public CommonResult(Integer code,String message)
  {
    this(code,message,null);
  }
}
