package com.sjk.es;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: WTS
 * 作者: WTS
 * 日期: 2020/9/28 19:25
 * 描述:
 */
@SpringBootApplication
@EnableDiscoveryClient
//@MapperScan(basePackages = {"com.sjk.es.mapper"})
@ComponentScan(basePackages = {"com.sjk"})
public class EsJobApplication {
    public static void main(String[] args) {
        SpringApplication.run(EsJobApplication.class);
    }
}
