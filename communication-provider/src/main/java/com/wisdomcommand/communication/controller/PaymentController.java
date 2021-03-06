package com.wisdomcommand.communication.controller;

import com.communication.common.CommonResult;
import com.communication.entity.Payment;
import com.javadaily.component.logging.annotation.SysLog;
import com.wisdomcommand.communication.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
public class PaymentController
{

    @Autowired
    PaymentService paymentService;
    @SysLog("查找用户")
    @GetMapping(value = "/paymentSQL/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id)
    {
        CommonResult<Payment> result = CommonResult.ok(paymentService.paymentSQL(id));
        return result;
    }



}
