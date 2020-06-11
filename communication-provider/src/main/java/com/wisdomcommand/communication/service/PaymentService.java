package com.wisdomcommand.communication.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.communication.entity.Payment;

public interface PaymentService   extends IService<Payment>  {

  Payment paymentSQL(Long id);

  void addPayment(Payment p);
}
