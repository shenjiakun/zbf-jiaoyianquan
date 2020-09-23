package com.zbf.user.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.sjk.utils.RandomUtil;
import com.sjk.utils.StringUtil;
import com.zbf.common.entity.AllRedisKeyEnum;
import com.zbf.common.entity.ResponseResult;
import com.zbf.common.entity.enums.SexEnum;
import com.zbf.common.entity.my.BaseUser;
import com.zbf.common.utils.*;
import com.zbf.user.service.IBaseUserService;
import com.zbf.user.api.ServiceYan;
import io.jsonwebtoken.ExpiredJwtException;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 申嘉坤
 * @since 2020-09-11
 */
@RestController
public class BaseUserController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    ServiceYan baseUser;

    @Autowired
    IBaseUserService iBaseUserService;

    ResponseResult responseResult = new ResponseResult();

    /**
     * 描述: 账户的激活地址
     */
    @Value("${active.path}")
    private String activePath;


    private static final Pattern PATTERN_PHONE = Pattern.compile("^-?\\d+(\\.\\d+)?$");



    /**
     * @Author 申嘉坤
     * @Description //TODO * @param tel
    * @param code
     * @Date 10:07 2020/9/14
     * @Param 
     * @return 注册
     **/
    @RequestMapping("/register")
    public ResponseResult test01(String tel,String code){

        System.out.println("tel=="+tel+"code=="+code);
        //存入redis
        String s = redisTemplate.opsForValue().get("codes");
        System.out.println("登录的验证"+s);
        if(s.equals(code)){
            responseResult.setCode(1006);
            responseResult.setSuccess(s);
            redisTemplate.delete("codes");
            return responseResult;
        }else {
            return null;
        }
    }

    /**
     * @Author 申嘉坤
     * @Description //TODO * @param phone
     * @Date 10:07 2020/9/14
     * @Param 
     * @return 注册验证手机号
     **/
    @RequestMapping("/Yan")
    public String yanzheng(String phone){
        if(!StringUtil.gitPhone(phone)){
            return "1003";
        }
        System.out.println(phone);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("tel",phone);
        BaseUser one = iBaseUserService.getOne(queryWrapper);
        System.out.println("==="+one);
        if(one==null){
            boolean loginyan = baseUser.loginyan(phone);
            System.out.println(loginyan);
            if(loginyan){
                //该手机号已存在用户
                return "1006";
            }else{
                //今日手机已不可以收短息
                return "1005";
            }
        }else {
            //请输入正确的手机号
            return "1004";
        }
    }


    /**
     * @Author 申嘉坤
     * @Description //TODO * @param phone
     * @Date 10:08 2020/9/14
     * @Param 
     * @return 修改密码验证
     **/
    @RequestMapping("/UpdateYan")
    public boolean UpdateYan(String phone){
        if(StringUtil.gitEmail(phone)){
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("email",phone);
            BaseUser one = iBaseUserService.getOne(queryWrapper);
            if(one!=null){
                System.out.println("======"+phone);
                String s = RandomUtil.randomNumber(4);
                System.out.println(s);
                redisTemplate.opsForValue().set("codes",s);
                MailQQUtils.sendMessage(phone,s,"申嘉坤邮箱","");
                return true;
            }
            return false;
        }else if(isPhone(phone)){
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("tel",phone);
            BaseUser one = iBaseUserService.getOne(queryWrapper);
            System.out.println(one);
            if(one!=null){
                System.out.println(phone) ;
                System.out.println("++++++++++++++++=");
                boolean b = baseUser.loginyan(phone);
                return b;
            }else {
                return false;
            }

        }else{
            return false;
        }
    }



    @RequestMapping("/BiDui")
    public ResponseResult BiDui(String tel,String code){
            System.out.println("tel=="+tel+"code=="+code);
            String s = redisTemplate.opsForValue().get("codes");
            System.out.println("登录的验证"+s);
            if(s.equals(code)){
                //1006登录成功
                responseResult.setCode(1006);
                responseResult.setSuccess(s);
                redisTemplate.delete("codes");
                return responseResult;
            }else {
                return null;
            }
        }

    //枚举
    @RequestMapping("/Sex")
    public SexEnum[] sexlist(){
        System.out.println("==================");
        SexEnum[] values = SexEnum.values();
        return values;
    }


    @RequestMapping("/Add")
    public ResponseResult add(@RequestBody BaseUser baseUser){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("email",baseUser.getEmail());
        BaseUser one = iBaseUserService.getOne(queryWrapper);
        System.out.println("------"+one);
        if(one!=null){
            responseResult.setCode(1005);
            return responseResult;
        }
        QueryWrapper queryWrapper1 = new QueryWrapper();
        queryWrapper.eq("loginName",baseUser.getLoginName());
        BaseUser one1 = iBaseUserService.getOne(queryWrapper);
        System.out.println(baseUser);
        if(one1!=null){
            responseResult.setCode(1004);
            return responseResult;
        }
        String s = RandomUtil.randomNumber(4);
        String encodePass= Md5.encode(baseUser.getPassWord()+s, "MD5");
        System.out.println(encodePass);
        BaseUser user = new BaseUser(baseUser.getId(),
                baseUser.getUserName(),
                baseUser.getLoginName(),
                encodePass,
                baseUser.getTel(),
                baseUser.getSex(),
                baseUser.getEmail(),
                s);
        user.setId(Long.valueOf(time()));
        user.setStatus(0);
        boolean save = iBaseUserService.save(user);
        if(save){
            responseResult.setCode(1006);
            String loginName = baseUser.getLoginName();
            //3、扣扣邮箱发送激活邮件
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() { MailQQUtils.sendMessage2("2463652789@qq.com","三斗网络",getActivePath(activePath,1*60*1000L,loginName));
                }
            });

            thread.start();
            return responseResult;
        }else{
            return null;
        }
    }

    /**
     * @Author 申嘉坤
     * @Description //TODO * @param
     * @Date 8:28 2020/9/18
     * @Param
     * @return 用户Id根据当前时间方法添加id
     **/
    public String time(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String format = simpleDateFormat.format(date);
        String response = format.replaceAll("[[\\s-:punct:]]","");
        return response;
    }





    /**
     * 作者: LCG
     * 日期: 2020/9/10  15:13
     * 参数：
     * 返回值：
     * 描述: 激活账户
     */
    @RequestMapping("activeUser")
    public void activeUser(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
        //获取激活的串
        String actived = request.getParameter("actived");
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();

        //设置响应头的格式
        response.setContentType("text/html;charset=UTF-8");
        //解析激活串
        try{
            JSONObject jsonObject = JwtUtilsForOther.decodeJwtTocken(actived);

            JSONObject info = JSON.parseObject(jsonObject.getString("info"));

            //获取存储的激活码
            String code = redisTemplate.opsForValue().get(AllRedisKeyEnum.ACTIVIVE_KEY.getKey() + "_" + info.get("loginName"));

            //激活成功后跳转到激活成功页面
            //在激活成功的页面可以跳转到登录界面，进行登录
            //如果激活码正确
            if(info.get("code").equals(code)){
                String loginName = request.getParameter("loginName");
                System.err.println(loginName);
                QueryWrapper<BaseUser> userQueryWrapper = new QueryWrapper<>();
                userQueryWrapper.eq("loginName",loginName);
                BaseUser one = iBaseUserService.getOne(userQueryWrapper);
                one.setStatus(1);
                iBaseUserService.updateById(one);
                stringObjectHashMap.put("loginPath","http://localhost:8080/");
                FreemarkerUtils.getStaticHtml(RestController.class,"/template/","activeOk.html",stringObjectHashMap,response.getWriter());
            }else{

                String loginName = request.getParameter("loginName");
                stringObjectHashMap.put("loginPath","http://192.168.132.1:10000/user/getNewActiveLink?loginName="+loginName);
                FreemarkerUtils.getStaticHtml(RestController.class,"/template/","activeError.html",stringObjectHashMap,response.getWriter());
            }

        }catch (ExpiredJwtException e){
            HashMap<String, Object> newData = new HashMap<>();
            String loginName = request.getParameter("loginName");
            newData.put("newActiveLink","http://192.168.132.1:10000/user/getNewActiveLink?loginName="+loginName);
            FreemarkerUtils.getStaticHtml(RestController.class,"/template/","activeError.html",newData,response.getWriter());

        }

    }

    /**
     * 作者: LCG
     * 日期: 2020/9/10  15:46
     * 参数：baseActivePath 激活的基本路劲，激活信息,timeOut 有效期
     * 返回值：
     * 描述: 这是一个普通的方法用来生成激活链接
     */
    public String getActivePath(String baseActivePath,long timeOut,String loginName){
        //激活信息
        String code= UID.getUUID16();
        //放入redis 中
        String key=AllRedisKeyEnum.ACTIVIVE_KEY.getKey()+"_"+loginName;
        redisTemplate.opsForValue().set(key,code);
        //设置redis的key过期时间
        redisTemplate.expire(key,timeOut,TimeUnit.MILLISECONDS);
        //生成激活的链接地址
        StringBuffer stringBuffer=new StringBuffer(activePath);
        stringBuffer.append("?");
        stringBuffer.append("loginName="+loginName);
        stringBuffer.append("&");
        stringBuffer.append("actived=");
        Map<String,String> map=new HashMap<>();
        map.put("loginName",loginName);
        map.put("code",code);
        stringBuffer.append(JwtUtilsForOther.generateToken(JSON.toJSONString(map),timeOut));
        String path=stringBuffer.toString();
        stringBuffer=null;
        return path;
    }


    /**
     * 作者: LCG
     * 日期: 2020/9/14  9:24
     * 描述: 激活失败重新获取激活链接邮件
     * @Param [request, response]
     * @Return void
     */
    @RequestMapping("getNewActiveLink")
    public void getNewActiveLink(HttpServletRequest request,HttpServletResponse response) throws Exception {

        HashMap<String, Object> stringObjectHashMap = new HashMap<>();

        //设置响应头的格式
        response.setContentType("text/html;charset=UTF-8");

        //如果jwt过期，重新的发激活邮件
        String loginName = request.getParameter("loginName");
        //根据loginName获取用户信息

        //3、扣扣邮箱发送激活邮件
        MailQQUtils.sendMessage2("2463652789@qq.com","三斗网络",getActivePath(activePath,1*60*1000L,loginName));

        FreemarkerUtils.getStaticHtml(RestController.class,"/template/","sendOK.html",stringObjectHashMap,response.getWriter());
    }




















    /**
     * @Author 申嘉坤
     * @Description //TODO * @param passWord
    * @param phone
     * @Date 10:09 2020/9/14
     * @Param 
     * @return 修改密码
     **/
    @RequestMapping("/Update")
    public boolean upda(String passWord,String phone){
        System.out.println("pass"+passWord+"ph"+phone);
        String s = RandomUtil.randomNumber(4);
        String encodePass= Md5.encode(passWord+s, "MD5");
        QueryWrapper queryWrapper = new QueryWrapper();
        if(StringUtil.gitEmail(phone)){
            queryWrapper.eq("email",phone);
        }
        if(isPhone(phone)){
            queryWrapper.eq("tel",phone);
        }
        BaseUser baseUser1 = new BaseUser(encodePass,s);
        boolean b = iBaseUserService.saveOrUpdate(baseUser1, queryWrapper);
        System.out.println(b);
        return b;
    }



    public boolean isPhone(String phone){
        return PATTERN_PHONE.matcher(phone).matches();
    }

}

