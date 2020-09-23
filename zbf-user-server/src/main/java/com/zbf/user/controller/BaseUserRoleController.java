package com.zbf.user.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zbf.common.entity.ResponseResult;
import com.zbf.common.entity.my.BaseUser;
import com.zbf.common.entity.my.BaseUserRole;
import com.zbf.user.service.IBaseUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *绑定角色中间表
 * @author thyu
 * @since 2020-09-17
 */
@RestController
@RequestMapping("/baseUserRole")
public class BaseUserRoleController {

    @Autowired
    private IBaseUserRoleService iBaseUserRoleService;


    /**
     * @Author 申嘉坤
     * @Description //TODO * @param baseUserRole
     * @Date 8:00 2020/9/22
     * @Param 
     * @return 绑定角色的添加
     **/
   @RequestMapping("/addRoleUser")
   public boolean bindRoleForUser(@RequestBody BaseUserRole baseUserRole){
        Integer count = 0;
       QueryWrapper<BaseUserRole> userRoleQueryWrapper = new QueryWrapper<>();
       boolean save = false;
       for (int i = 0;i<baseUserRole.getRoids().length;i++){
           userRoleQueryWrapper.eq("roleId",Long.valueOf(baseUserRole.getRoids()[i]));
           List<BaseUserRole> list = iBaseUserRoleService.list(userRoleQueryWrapper);
           for (int j=0;j<list.size();j++){
               if (list.get(j).getUserId()==Long.valueOf(baseUserRole.getUserId())){
                   count++;
               }
           }
           if(count==0){
               BaseUserRole baseUserRole1 = new BaseUserRole();
               baseUserRole1.setUserId(Long.valueOf(baseUserRole.getUserId()));
               baseUserRole1.setRoleId(Long.valueOf(baseUserRole.getRoids()[i]));
              save = iBaseUserRoleService.save(baseUserRole1);
           }
           count=0;
       }
       return save;
   }
}

