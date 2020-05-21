package com.wisdomcommand.communication.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
@MapperScan({"com.wisdomcommand.communication.dao"})
public class MyBatisConfig {
}
