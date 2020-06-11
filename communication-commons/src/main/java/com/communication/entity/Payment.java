package com.communication.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "测试实体类",description = "测试")
public class Payment implements Serializable
{
    @ApiModelProperty(value = "产品主键")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "内容")
    private String serial;
}
