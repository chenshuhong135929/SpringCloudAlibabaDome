package com.wisdomcommand.communication.service;

import com.communication.common.CommonResult;
import com.communication.entity.Payment;
import com.wisdomcommand.communication.service.impl.PaymentFallbackService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 */
@FeignClient(value = "communication-provider",  contextId ="provider1",   fallback = PaymentFallbackService.class)
public interface PaymentService
{
    @GetMapping(value = "/paymentSQL/{id}")
    CommonResult<Payment> paymentSQL(@PathVariable("id") Long id);
}
