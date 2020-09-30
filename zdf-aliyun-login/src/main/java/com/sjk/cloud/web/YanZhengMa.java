package com.sjk.cloud.web;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import com.sjk.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description:
 * @projectName:zbf-jiaoyianquan
 * @see:gateway.filter
 * @author:申嘉坤
 * @createTime:2020/9/9 21:09
 * @version:1.0
 */
@RestController
@RequestMapping("/yan")
public class YanZhengMa {


    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @RequestMapping("/YanLogin")
    public boolean sendSms(@RequestParam("phone") String phone) throws ClientException {
        System.out.println(phone);
        String code = RandomUtil.randomNumber(4);

        //产品名称:云通信短信API产品,开发者无需替换
        String product = "Dysmsapi";
        //产品域名,开发者无需替换
        String domain = "dysmsapi.aliyuncs.com";

        // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
        String accessKeyId = "LTAI4G9sjMWfvs5F2gVGKLHR";
        String accessKeySecret = "aYPEkmd6d4Vk07SjofhbkPEjNQDgF4";


        System.out.println("phone=" + phone);
        System.out.println("code=" + code);
        //redisTemplate.opsForValue().set("codes","31231");
        redisTemplate.opsForValue().set("codes", code);

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "80000");
        System.setProperty("sun.net.client.defaultReadTimeout", "80000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("张张果冻营业");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_202550510");


        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam("{\"code\":" + code + "}");

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch

        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            //请求成功
            System.out.println(sendSmsResponse.getCode());
            System.out.println("请求成功");
            return true;
        }
        return false;
    }
}
