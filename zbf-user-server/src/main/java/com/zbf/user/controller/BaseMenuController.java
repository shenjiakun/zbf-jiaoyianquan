package com.zbf.user.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.zbf.common.entity.AllRedisKeyEnum;
import com.zbf.common.entity.ResponseResult;
import com.zbf.common.entity.my.BaseMenu;
import com.zbf.common.entity.my.BaseUser;
import com.zbf.common.exception.AllStatusEnum;
import com.zbf.user.service.IBaseMenuService;
import com.zbf.user.service.IBaseUserService;
import com.zbf.user.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author thyu
 * @since 2020-09-16
 */
@RestController
// 配置跨域注解
//@CrossOrigin
@RequestMapping("/menu")
public class BaseMenuController {



    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IBaseMenuService iBaseMenuService;


    @Autowired
    private IBaseUserService iBaseUserService;

    @Autowired
    private MenuService menuService;


    /**
     * @Author 申嘉坤
     * @Description //TODO * @param id
     * @Date 13:41 2020/9/17
     * @Param 
     * @return 通过用户名进入菜单
     **/
    @RequestMapping("/listMenu")
    public List<Map<String,Object>> listMenu(Integer id){
        BaseUser byId = iBaseUserService.getById(id);
        HashMap<String, Object> userHash = new HashMap<>();
        userHash.put("loginName",byId.getLoginName());
        userHash.put("id",byId.getId());
        List<Map<String, Object>> list = menuService.getlistMenu(userHash);
        return list;
    }

    /**
     * @Author 申嘉坤
     * @Description //TODO * @param loginName
     * @Date 13:42 2020/9/17
     * @Param 
     * @return 菜单管理列表
     **/
    @RequestMapping("/getMenuList")
    public List<BaseMenu> getMenuList(){

        return iBaseMenuService.listadmin();

    }

    public String time(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String format = simpleDateFormat.format(date);
        String response = format.replaceAll("[[\\s-:punct:]]","");
        return response;
    }

    @RequestMapping("/addMenu")
    public boolean addMenu(@RequestBody BaseMenu baseMenu){
            try {
                baseMenu.setId(Long.valueOf(time()));
                baseMenu.setCode(Long.valueOf(time()+1));
                iBaseMenuService.save(baseMenu);
                return true;
            }catch (Exception e){
                e.printStackTrace();
            }
        return false;
    }

    @RequestMapping("/updMenu")
    public boolean updMenu(@RequestBody BaseMenu baseMenu){
        try {
            iBaseMenuService.saveOrUpdate(baseMenu);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @RequestMapping("/delete")
   public boolean delMenu(Long id){
        try {
            BaseMenu byId = iBaseMenuService.getById(id);
            if(byId.getLeval()==1){
                iBaseMenuService.removeById(byId.getId());
                List<BaseMenu> getbycodes = iBaseMenuService.getbycodes(byId.getCode());
                for (int i=0;i<getbycodes.size();i++){
                    iBaseMenuService.removeById(getbycodes.get(i).getId());

                    List<BaseMenu> getbycodes1 = iBaseMenuService.getbycodes(getbycodes.get(i).getCode());
                    for(int j=0;j<getbycodes1.size();j++){
                        iBaseMenuService.removeById(getbycodes1.get(j).getId());
                    }
                }
                return true;
            }else if (byId.getLeval()==2){
                iBaseMenuService.removeById(byId.getId());
                List<BaseMenu> getbycodes = iBaseMenuService.getbycodes(byId.getCode());
                for(int i=0;i<getbycodes.size();i++){
                    iBaseMenuService.removeById(getbycodes.get(i).getId());
                }
            }else if (byId.getLeval()==3){
                return iBaseMenuService.removeById(byId.getId());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


}

