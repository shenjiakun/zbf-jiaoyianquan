package com.zbf.common.entity.my;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author thyu
 * @since 2020-09-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseUserRole implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("roleId")
    private Long roleId;

    @TableField("userId")
    private Long userId;

    @TableField(exist = false)
    private Integer[] roids;

}
