package com.zbf.user.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zbf.common.entity.my.BaseRole;
import com.zbf.common.entity.my.BaseUser;
import com.zbf.user.service.IBaseRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    public List<BaseRole> listAll(BaseRole role ,@RequestParam String name) {

//        List<BaseRole> list = iBaseRoleService.lambdaQuery().eq(null != role.getName() && role.getName().isEmpty(),BaseRole::getName,role.getName()).list();
        List<BaseRole> list = iBaseRoleService.selectList(role,name);
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


    @RequestMapping("/RoleSava")
    public boolean RoleSava(@RequestBody BaseRole baseRole){
            try {
//                baseRole.setId(Long.valueOf(time()));
                iBaseRoleService.save(baseRole);
                return true;
            }catch (Exception ex){
                ex.printStackTrace();
            }
        return false;
    }

    /**
     * @Author 申嘉坤
     * @Description //TODO * @param
     * @Date 8:28 2020/9/18
     * @Param
     * @return 菜单添加根据当前时间
     **/
    public String time(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String format = simpleDateFormat.format(date);
        String response = format.replaceAll("[[\\s-:punct:]]","");
        return response;
    }

}

