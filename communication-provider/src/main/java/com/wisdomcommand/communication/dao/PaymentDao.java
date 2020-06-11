package com.wisdomcommand.communication.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.communication.entity.Payment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentDao  extends BaseMapper<Payment>  {

}
