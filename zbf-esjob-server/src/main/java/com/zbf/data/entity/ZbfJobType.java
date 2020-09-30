package com.zbf.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author wts
 * @since 2020-09-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ZbfJobType implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * job编码
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * job描述
     */
    private String jobMark;

    /**
     * job类
     */
    private String jobClass;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private LocalDateTime createTime;


}
