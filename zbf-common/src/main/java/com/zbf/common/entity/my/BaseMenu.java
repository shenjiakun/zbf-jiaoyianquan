package com.zbf.common.entity.my;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 菜单
 * </p>
 *
 * @author thyu
 * @since 2020-09-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("base_menu")
public class BaseMenu implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 菜单表
     */
    private Long id;

    private Integer version;

    private Long code;

    @TableField("menuName")
    private String menuName;

    @TableField("imagePath")
    private String imagePath;

    private String url;

    @TableField("parentCode")
    private Long parentCode;

    private Integer leval;

    @TableField("createTime")
    private LocalDateTime createTime;

    @TableField(exist = false)
    private List<BaseMenu> list;



}
