package com.zbf.common.entity.xsl;

import lombok.Data;

/**
 * @description:
 * @projectName:zbf-jiaoyianquan
 * @see:com.zbf.common.entity.xsl
 * @author:申嘉坤
 * @createTime:2020/9/20 20:04
 * @version:1.0 ex工作表导出实体类
 */
@Data
public class ExClass {

    private Integer total;

    private String type[];

    private String order;

    private String orderType[];

    private String excleName;
}

