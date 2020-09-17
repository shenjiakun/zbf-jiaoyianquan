package com.zbf.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zbf.common.entity.my.BaseMenu;
import com.zbf.user.mapper.BaseMenuMapper;
import com.zbf.user.service.IBaseMenuService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author thyu
 * @since 2020-09-16
 */
@Service
public class BaseMenuServiceImpl extends ServiceImpl<BaseMenuMapper, BaseMenu> implements IBaseMenuService {


    @Override
    public List<BaseMenu> listadmin() {
        return baseMapper.listadmin();
    }

    @Override
    public List<BaseMenu> listByName(String loginName) {
        return baseMapper.listByName(loginName);
    }



    @Override
    public List<BaseMenu> getbycodes(Long code) {
        return baseMapper.getbycodes(code);
    }

}
