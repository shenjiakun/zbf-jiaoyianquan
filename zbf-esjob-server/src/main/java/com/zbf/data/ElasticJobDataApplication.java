package com.zbf.data;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: WTS
 * 作者: WTS
 * 日期: 2020/9/29 13:40
 * 描述:
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan(basePackages = {"com.zbf.data.mapper"})
@ComponentScan(basePackages = {"com.zbf"})
public class ElasticJobDataApplication {
    public static void main(String[] args) {
        SpringApplication.run(ElasticJobDataApplication.class);
    }
}
