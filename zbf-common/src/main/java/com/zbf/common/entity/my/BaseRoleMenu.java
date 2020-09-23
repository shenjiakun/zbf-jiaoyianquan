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
public class BaseRoleMenu implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 这是角色菜单关系表
     */
    private Long id;

    private Integer version;

    private Long roleId;

    /**
     * 角色菜单表
     */
    private Long menuId;



}
