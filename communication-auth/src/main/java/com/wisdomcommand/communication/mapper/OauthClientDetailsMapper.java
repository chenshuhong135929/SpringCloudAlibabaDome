package com.wisdomcommand.communication.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wisdomcommand.communication.entity.OauthClientDetails;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OauthClientDetailsMapper extends BaseMapper<OauthClientDetails> {


  long addOauthClientDetails(OauthClientDetails oauthClientDetails);


}
