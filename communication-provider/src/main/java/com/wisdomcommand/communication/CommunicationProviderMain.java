package com.wisdomcommand.communication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 *
 */
@EnableDiscoveryClient
@SpringBootApplication
public class CommunicationProviderMain
{

    public static void main(String[] args)
    {
        SpringApplication.run(CommunicationProviderMain.class, args);
    }
}
