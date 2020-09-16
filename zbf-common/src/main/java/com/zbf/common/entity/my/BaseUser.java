package com.zbf.common.entity.my;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zbf.common.entity.enums.SexEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author 申嘉坤
 * @since 2020-09-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("base_user")
public class BaseUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户表
     */
    @TableId("id")
    private Long id;

    @TableField("version")
    private Integer version;

    //用户名
    @TableField("userName")
    private String userName;

    //登录名
    @TableField("loginName")
    private String loginName;

    //密码
    @TableField("passWord")
    private String passWord;

    @TableField("tel")
    private String tel;

    //版本
    @TableField("buMen")
    private String buMen;

    @TableField("salt")
    private String salt;

    @TableField("createTime")
    private LocalDateTime createTime;

    @TableField("updateTime")
    private LocalDateTime updateTime;

    //性别
    @TableField("sex")
    private SexEnum sex;

    //邮箱
    @TableField("email")
    private String email;

    //邮箱
    @TableField("status")
    private Integer status;

    public BaseUser(Long id, String userName, String loginName, String passWord, String tel, SexEnum sex, String email, String salt) {
        this.id = id;
        this.userName = userName;
        this.loginName = loginName;
        this.passWord = passWord;
        this.tel = tel;
        this.sex = sex;
        this.email = email;
        this.salt = salt;
    }

    public BaseUser(String passWord, String salt) {
        this.passWord = passWord;
        this.salt = salt;
    }

    public BaseUser(Long id, Integer version, String userName, String loginName, String passWord, String tel, String buMen, String salt, LocalDateTime createTime, LocalDateTime updateTime, SexEnum sex, String email) {
        this.id = id;
        this.version = version;
        this.userName = userName;
        this.loginName = loginName;
        this.passWord = passWord;
        this.tel = tel;
        this.buMen = buMen;
        this.salt = salt;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.sex = sex;
        this.email = email;
    }

    public BaseUser() {
    }
}
