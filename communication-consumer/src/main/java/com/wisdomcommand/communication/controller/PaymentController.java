package com.wisdomcommand.communication.controller;

import com.chenshuhong.springcloud.entities.CommonResult;
import com.chenshuhong.springcloud.entities.Payment;
import com.wisdomcommand.communication.service.PaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@Api(tags = "communication-consumer模块")
public class PaymentController {
  @Resource
  private PaymentService paymentService;

  @ApiOperation(value = "调试")
  @GetMapping(value = "/paymentSQL/{id}")
  @ApiImplicitParam(name = "id",value = "主键ID", required = true,paramType = "path")
  public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id)
  {
    return paymentService.paymentSQL(id);
  }
}
