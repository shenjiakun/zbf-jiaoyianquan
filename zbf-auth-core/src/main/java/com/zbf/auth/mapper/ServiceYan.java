package com.zbf.auth.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description:
 * @projectName:zbf-jiaoyianquan
 * @see:com.zbf.auth.mapper
 * @author:袁成龙
 * @createTime:2020/9/10 14:36
 * @version:1.0
 */
@FeignClient(value = "provider")
public interface ServiceYan {

    @RequestMapping("/yan/YanLogin")
    public boolean loginyan(@RequestParam("phone") String phone);
}
