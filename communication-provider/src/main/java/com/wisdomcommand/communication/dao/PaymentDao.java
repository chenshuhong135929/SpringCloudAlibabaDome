package com.wisdomcommand.communication.dao;

import com.communication.common.Payment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentDao {

  Payment paymentSQL(Long  id);

  Long addPayment(Payment p);
}
