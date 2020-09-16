package com.zbf.auth.denglu;

import com.zbf.auth.mapper.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author: LCG
 * 作者: LCG
 * 日期: 2020/8/26 20:37
 * 描述: 这个类主要是用来根据传入的用户名获取用户信息的
 */
@Component
public class PhoneYan implements UserDetailsService {

    /**
     * 作者: LCG
     * 日期: 2020/9/8  12:44
     * 描述:  校验手机号的判断
     */
    private static final Pattern PATTERN_PHONE = Pattern.compile("^-?\\d+(\\.\\d+)?$");

    @Autowired(required = false)
    private UserDao userDao;


    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 根据用户获取
     *
     * @param
     * @return
     * @throws UsernameNotFoundException
     */


    public boolean isPhone(String phone){
        return PATTERN_PHONE.matcher(phone).matches();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //手机验证码
        if (isPhone(s)) {
            System.out.println("===============手机验证码"+s);
            //查询用户信息
            Map<String, Object> userByUserName = userDao.getUserByPhone(s);
            System.out.println("执行了++++++++++++++" + userByUserName);
            System.out.println(userByUserName);

            if (userByUserName != null) {
                //根据用户查询用户的角色信息
                List<String> userRoleCodes = userDao.getUserRole(Long.valueOf(userByUserName.get("id").toString()));
                List<GrantedAuthority> list = new ArrayList<>();

                userRoleCodes.forEach(roleCode -> {
                    SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roleCode);
                    list.add(simpleGrantedAuthority);
                    simpleGrantedAuthority = null;
                });

                //创建用户对象
                User user = new User(userByUserName.get("loginName").toString(), userByUserName.get("passWord").toString(), list);
                System.out.println("user的值====" + user);
                Boolean cede = redisTemplate.delete("codes");
                System.out.println(cede);
                if (cede) {
                    System.out.println("删除成功");
                    return user;
                }
            } else {
                throw new BadCredentialsException("用户名或密码不正确");
            }
        }
        return null;
    }
}
