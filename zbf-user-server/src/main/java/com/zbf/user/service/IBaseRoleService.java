package com.zbf.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zbf.common.entity.my.BaseRole;

import java.util.List;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author thyu
 * @since 2020-09-17
 */
public interface IBaseRoleService extends IService<BaseRole> {

    List<BaseRole> selectList(BaseRole role, String name);
}
