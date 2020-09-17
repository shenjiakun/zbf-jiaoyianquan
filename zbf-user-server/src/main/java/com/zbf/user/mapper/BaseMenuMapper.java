package com.zbf.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zbf.common.entity.my.BaseMenu;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author thyu
 * @since 2020-09-16
 */
public interface BaseMenuMapper extends BaseMapper<BaseMenu> {


    @Select("select distinct bu.id userId,bu.loginName,bm.code,bm.id menuId,bm.menuName,bm.url,bm.leval,bm.imagePath from base_user_role bur INNER JOIN base_user bu on bu.id = bur.userId\n" +
            "INNER JOIN base_role_menu brm on brm.role_id=bur.roleId\n" +
            "INNER JOIN base_menu bm on bm.id = brm.menu_id\n" +
            "where bu.loginName=#{loginName} and bm.leval=1")
    List<Map<String,Object>> getByLoginNameMenu(String loginName);

    @Select("select distinct bu.id userId,bu.loginName,bm.code,bm.id menuId,bm.menuName,bm.url,bm.leval,bm.imagePath from base_user_role bur INNER JOIN base_user bu on bu.id = bur.userId\n" +
            "INNER JOIN base_role_menu brm on brm.role_id=bur.roleId\n" +
            "INNER JOIN base_menu bm on bm.id = brm.menu_id\n" +
            "where bu.loginName=#{loginName} and bm.leval=#{leval} and bm.parentCode=#{parentCode}")
    List<Map<String, Object>> getNextMenu(HashMap<Object, Object> paramHash);

    List<BaseMenu> listadmin();

    List<BaseMenu> listByName(String loginName);

    @Select("select * from base_menu where parentCode = ${value}")
    List<BaseMenu> getbycodes(Long code);
}
