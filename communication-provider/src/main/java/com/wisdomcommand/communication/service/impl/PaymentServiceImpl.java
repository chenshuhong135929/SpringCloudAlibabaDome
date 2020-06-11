package com.wisdomcommand.communication.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.communication.entity.Payment;
import com.wisdomcommand.communication.dao.PaymentDao;
import com.wisdomcommand.communication.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PaymentServiceImpl extends ServiceImpl<PaymentDao, Payment> implements PaymentService {

  @Resource
  PaymentDao paymentMapper;

  @Override
  public Payment paymentSQL(Long id) {
    return this.getById(id);
  }


  @DS("slave")
  @Override
  public void addPayment(Payment p) {
    paymentMapper.insert(p);

  }
}
