package com.zbf.auth.web;


import com.sjk.utils.RandomUtil;
import com.sjk.utils.StringUtil;
import com.zbf.auth.mapper.ServiceYan;
import com.zbf.common.utils.MailQQUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Pattern;

/**
 * @description:
 * @projectName:zbf-jiaoyianquan
 * @see:com.zbf.auth.denglu
 * @author:申嘉坤
 * @createTime:2020/9/10 14:15
 * @version:1.0
 */
@RestController
public class YanZhengController {

    @Autowired
    private ServiceYan yanFegin;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    private static final Pattern PATTERN_PHONE = Pattern.compile("^-?\\d+(\\.\\d+)?$");

    @RequestMapping("/Yan")
    public boolean yanzheng(String phone){
        if(StringUtil.gitEmail(phone)){
            System.out.println("======"+phone);
            String s = RandomUtil.randomNumber(4);
            System.out.println(s);
            redisTemplate.opsForValue().set("codes",s);
            MailQQUtils.sendMessage(phone,s,"申嘉坤邮箱","");
            return true;
        }else if(isPhone(phone)){
            System.out.println(phone);
            System.out.println("++++++++++++++++=");
            boolean b = yanFegin.loginyan(phone);
            return b;
        }else{
            return false;
        }
    }


    public boolean isPhone(String phone){
        return PATTERN_PHONE.matcher(phone).matches();
    }


}