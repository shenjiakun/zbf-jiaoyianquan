package com.zbf.common.entity.my;

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
public class BaseRole implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 这是角色表
     */
    private Long id;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色表
     */
    private String name;

    /**
     * 描述
     */
    private String miaoshu;


}
