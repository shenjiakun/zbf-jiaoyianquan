package com.zbf.common.utils;

import java.security.MessageDigest;


/**
 * @description:
 * @projectName:zbf-jiaoyianquan
 * @see:com.zbf.common.utils
 * @author:申嘉坤
 * @createTime:2020/9/12 10:20
 * @version:1.0
 */
public class Md5 {
    public static String encode(String password, String algorithm) {
        byte[] unencodedPassword = password.getBytes();
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (Exception e) {
            return password;
        }
        md.reset();
        md.update(unencodedPassword);
        byte[] encodedPassword = md.digest();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < encodedPassword.length; i++) {
            if ((encodedPassword[i] & 0xFF) < 16) {
                buf.append("0");
            }
            buf.append(Long.toString(encodedPassword[i] & 0xFF, 16));
        }
        return buf.toString();


    }
}