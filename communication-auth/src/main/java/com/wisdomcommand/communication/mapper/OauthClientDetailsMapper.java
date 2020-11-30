package com.wisdomcommand.communication.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wisdomcommand.communication.entity.OauthClientDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OauthClientDetailsMapper extends BaseMapper<OauthClientDetails> {


  long addOauthClientDetails(OauthClientDetails oauthClientDetails);

  long selectByClientId(@Param("clientId")  String clientId);

}
