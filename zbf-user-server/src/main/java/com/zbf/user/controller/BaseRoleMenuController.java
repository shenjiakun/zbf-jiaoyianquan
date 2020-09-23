package com.zbf.user.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zbf.common.entity.ResponseResult;
import com.zbf.common.entity.my.BaseMenu;
import com.zbf.common.entity.my.BaseRoleMenu;
import com.zbf.user.service.IBaseMenuService;
import com.zbf.user.service.IBaseRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 *  
 * </p>
 *
 * @author thyu
 * @since 2020-09-17
 */
@RestController
@RequestMapping("/baseRoleMenu")
public class BaseRoleMenuController {

    @Autowired
    IBaseRoleMenuService iBaseRoleMenuService;

    @RequestMapping("/savaRoleMenu")
    public boolean savaRoleMenu(@RequestParam  Long  rid,@RequestParam Long mids[]){

            System.out.println(rid);
            QueryWrapper<BaseRoleMenu> wrapper = new QueryWrapper<>();
            wrapper.eq("role_id",rid);
            iBaseRoleMenuService.remove(wrapper);
            boolean save=false;
            for (int i = 0; i < mids.length;i++){
                System.out.println(mids[i]);
                BaseRoleMenu baseRoleMenu = new BaseRoleMenu();
                baseRoleMenu.setRoleId(rid);
                baseRoleMenu.setMenuId(mids[i]);
                System.out.println(mids[i]);
                save = iBaseRoleMenuService.save(baseRoleMenu);
                System.out.println(save);
            }

        return save;
    }

}

