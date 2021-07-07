package com.communication.common;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "返回封装类",description = "返回封装类")
public class CommonResult<T>
{
  @ApiModelProperty(value = "状态码")
  private Integer code;
  @ApiModelProperty(value = "提示信息")
  private String  message;
  @ApiModelProperty(value = "jwtToken")
  private String  jwtToken;
  @ApiModelProperty(value = "数据")
  private T       data;

  public CommonResult(Integer code,String message)
  {
    this(code,message,null,null);
  }
  public static <T> CommonResult<T> ok(T result) {
    CommonResultBuilder<T> builder = builder();
    return builder
        .data(result)
        .code(ResultEnum.SUCCESS.getCode())
        .message(ResultEnum.SUCCESS.getMessage())
        .build();
  }

  public static <T> CommonResult<T> ok() {
    return ok(null);
  }


  public static <T> CommonResult<T> error( String message) {
    CommonResultBuilder<T> builder = builder();
    return builder
        .message(message)
        .code(ResultEnum.ERROR.getCode())
        .build();
  }
  public static <T> CommonResult<T> error(int code, String message) {
    CommonResultBuilder<T> builder = builder();
    return builder
        .message(message)
        .code(code)
        .build();
  }

  /**
   * 微服务调用后，获取数据
   * @return  数据
   */
  public T get(){
    if(this.code!=200){
      throw new CommonsException(this.code,this.message);
    }
    return this.data;
  }

  /**
   * 微服务调用后检验是否成功，常用于微服务返回类型为void
   */
  public void checkSuccess() {
    if (this.code != 200) {
      throw new CommonsException(this.code,this.message);
    }
  }
}
