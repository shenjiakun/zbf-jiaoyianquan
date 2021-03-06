package com.zbf.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zbf.common.entity.my.BaseMenu;

import java.util.List;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author thyu
 * @since 2020-09-16
 */
public interface IBaseMenuService extends IService<BaseMenu> {


    List<BaseMenu> listadmin();

    List<BaseMenu> listByName(String loginName);



    List<BaseMenu> getbycodes(Long code);

    List<BaseMenu> findByRoleMenu(Long roleId);
}
