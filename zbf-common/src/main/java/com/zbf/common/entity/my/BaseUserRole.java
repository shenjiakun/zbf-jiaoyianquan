package com.zbf.common.entity.my;

import com.baomidou.mybatisplus.annotation.TableField;
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

    private Long id;

    @TableField("roleId")
    private Long roleId;

    @TableField("userId")
    private Long userId;


}
