package com.zbf.user.api;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 每次记得修改mapper自己的扫描包
 */
@Configuration  //相当于写了个spring的xml配置
@MapperScan("com.zbf.user.mapper")
public class MyBatisPlusConfig {

    @Bean
    public PaginationInterceptor PaginationInterceptor(){
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        //设置拦截器分页参数
        return  paginationInterceptor;
    }
}
