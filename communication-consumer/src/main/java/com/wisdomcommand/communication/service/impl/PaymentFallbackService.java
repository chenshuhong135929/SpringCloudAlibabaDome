package com.wisdomcommand.communication.service.impl;

import com.communication.common.CommonResult;
import com.communication.entity.Payment;
import com.wisdomcommand.communication.service.PaymentService;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class PaymentFallbackService implements PaymentService
{
    @Override
    public CommonResult<Payment> paymentSQL(Long id)
    {
        return new CommonResult<>(44444,"服务降级返回,---PaymentFallbackService",new Payment(id,"errorSerial"));
    }
}
