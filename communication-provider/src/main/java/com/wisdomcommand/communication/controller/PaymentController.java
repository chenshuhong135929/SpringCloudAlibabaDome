package com.wisdomcommand.communication.controller;

import com.communication.common.CommonResult;
import com.communication.common.Payment;
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

    @GetMapping(value = "/paymentSQL/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id)
    {
        CommonResult<Payment> result = new CommonResult(200,"from mysql,serverPort:  ",paymentService.paymentSQL(id));
        return result;
    }



}
