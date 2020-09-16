package com.zbf.user.service;

import com.zbf.common.entity.my.BaseUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 申嘉坤
 * @since 2020-09-11
 */
public interface IBaseUserService extends IService<BaseUser> {

       /* @Autowired
    private BaseMenuMapper menuDao;

    public void getUserNextMenu(List<Map<String,Object>> list,String loginName){

        for (Map<String,Object> menu:list){
            Map<String,Object> parm = new HashMap<>();
            parm.put("loginName",loginName);
            parm.put("leval",Integer.valueOf(menu.get("leval").toString()+1));
            parm.put("parentCode",menu.get("code"));
            List<Map<String,Object>> userNextMenu = menuDao.getUserNextMenu(parm);
            if (userNextMenu.size()>0){
                menu.put("listMenu",userNextMenu);

                this.getUserNextMenu(userNextMenu,loginName);
            }else {
                userNextMenu=new ArrayList<>();
                menu.put("listMenu",userNextMenu);
                break;
            }
        }
    }

    public void getUserNextMenu(Map<String, Object> map) {
    }*/
}
