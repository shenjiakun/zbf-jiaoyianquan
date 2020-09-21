package com.zbf.user.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zbf.common.entity.my.BaseRole;
import com.zbf.user.service.IBaseRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author thyu
 * @since 2020-09-17
 */
@RestController
@RequestMapping("/baseRole")
public class BaseRoleController {

    /**
     * @Author 申嘉坤
     * @Description //TODO * @param null
     * @Date 11:11 2020/9/19
     * @Param 
     * @return 绑定角色查询
     **/
    @Autowired
    private IBaseRoleService iBaseRoleService;
    @RequestMapping("/listAll")
    public List<BaseRole> listAll(BaseRole role) {
        List<BaseRole> list = iBaseRoleService.list();
        return list;
    }

    /**
     * @Author 申嘉坤
     * @Description //TODO * @param page
    * @param role
     * @Date 11:11 2020/9/19
     * @Param 
     * @return 角色列表
     **/
    @RequestMapping("/list")
    public IPage<BaseRole> list(Page page, BaseRole role) {
        QueryWrapper<BaseRole> wapper = null;
        if (role != null) {
            wapper = new QueryWrapper<>();
            if (role.getName() != null && !"".equals(role.getName().trim())) {
                wapper.like("name", role.getName());
            }

        }
        return iBaseRoleService.page(page, wapper);
    }

}

