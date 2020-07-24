package com.wisdomcommand.communication.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.communication.common.CommonResult;
import com.communication.entity.Payment;
import com.wisdomcommand.communication.service.PaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
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

  @Autowired
  ApplicationEventPublisher applicationEventPublisher;

  @ApiOperation(value = "调试")
  @GetMapping(value = "/paymentSQL/{id}")
  @ApiImplicitParam(name = "id",value = "主键ID", required = true,paramType = "path")
  public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id)
  {
    CommonResult<Payment> paymentCommonResult = paymentService.paymentSQL(id);
    applicationEventPublisher.publishEvent(paymentCommonResult.getData());
    return paymentCommonResult;
  }
//事件回掉示例
  @EventListener
  @Async
  public void  evetPayment(Payment e){
   System.out.println("事件回掉  "+e.getSerial());
  }

}
