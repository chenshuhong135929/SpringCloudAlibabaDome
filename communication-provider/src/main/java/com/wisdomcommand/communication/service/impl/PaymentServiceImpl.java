package com.wisdomcommand.communication.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.communication.common.Payment;
import com.wisdomcommand.communication.dao.PaymentDao;
import com.wisdomcommand.communication.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PaymentServiceImpl implements PaymentService {

  @Resource
  PaymentDao paymentMapper;

  @Override
  public Payment paymentSQL(Long id) {
    return paymentMapper.paymentSQL(id);
  }


  @DS("slave")
  @Override
  public Long addPayment(Payment p) {
   return  paymentMapper.addPayment(p);
  }
}
