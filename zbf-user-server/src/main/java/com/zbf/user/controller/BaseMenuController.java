package com.zbf.user.controller;


import com.zbf.common.entity.my.BaseMenu;
import com.zbf.common.entity.my.BaseUser;
import com.zbf.user.service.IBaseMenuService;
import com.zbf.user.service.IBaseUserService;
import com.zbf.user.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author thyu
 * @since 2020-09-16
 */
@RestController
@CrossOrigin
@RequestMapping("/menu")
public class BaseMenuController {



    @Autowired
    private RedisTemplate redisTemplate;

   /* @Autowired
    private MenuService menuService;*/

    @RequestMapping("/listAll")
    public List<BaseMenu> listAll() {
        return iBaseMenuService.findByPid(1);
    }

    @RequestMapping("/findByloginName")
    public List<BaseMenu> findByloginName(String loginName) {
        return iBaseMenuService.findByloginName(loginName);
    }

    /*@RequestMapping("/getAuthMenu")
    public ResponseResult getAuthList(@RequestBody Map<String,Object> map){
        ResponseResult responseResult = ResponseResult.getResponseResult();

        String authMenuId = AllRedisKeyEnum.USER_MENU_KEY.getKey()+map.get("loginName");
        String menuList = (String) redisTemplate.opsForValue().get(authMenuId);
        if (menuList == null || menuList.equals("")){
            menuService.getUserNextMenu(map);
        }
        return null;
    }*/

    @Autowired
    private IBaseMenuService iBaseMenuService;


    @Autowired
    private IBaseUserService iBaseUserService;

    @Autowired
    private MenuService menuService;


    @RequestMapping("/listMenu")
    public List<Map<String,Object>> listMenu(Integer id){
        BaseUser byId = iBaseUserService.getById(id);
        HashMap<String, Object> userHash = new HashMap<>();
        userHash.put("loginName",byId.getLoginName());
        userHash.put("id",byId.getId());
        List<Map<String, Object>> list = menuService.getlistMenu(userHash);
        return list;
    }

}

