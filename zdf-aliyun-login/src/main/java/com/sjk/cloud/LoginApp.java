package com.sjk.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @description:
 * @projectName:zbf-jiaoyianquan
 * @see:com.ycl.cloud
 * @author:申嘉坤
 * @createTime:2020/9/10 13:55
 * @version:1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class LoginApp {

    public static void main(String[] args) {
        SpringApplication.run(LoginApp.class);
    }



}