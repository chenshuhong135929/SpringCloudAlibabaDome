package com.wisdomcommand.communication.service;

import com.communication.common.Payment;

public interface PaymentService {

  Payment paymentSQL(Long id);

  Long addPayment(Payment p);
}
