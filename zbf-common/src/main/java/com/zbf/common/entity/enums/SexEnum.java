package com.zbf.common.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @description:
 * @projectName:zbf-jiaoyianquan
 * @see:com.zbf.common.entity
 * @author:申嘉坤
 * @createTime:2020/9/11 14:09
 * @version:1.0
 */
@Getter
public enum SexEnum {

    MAN("男",1),WOMAN("女",0);

    @JsonValue
    private String label;

    @EnumValue
    private Integer value;

    SexEnum(String label,Integer value){
        this.label=label;
        this.value=value;
    }

}
