package com.zbf.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zbf.common.entity.my.BaseRole;
import com.zbf.user.mapper.BaseRoleMapper;
import com.zbf.user.service.IBaseRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author thyu
 * @since 2020-09-17
 */
@Service
public class BaseRoleServiceImpl extends ServiceImpl<BaseRoleMapper, BaseRole> implements IBaseRoleService {

    @Override
    public List<BaseRole> selectList(BaseRole role, String name) {
        return baseMapper.selectList(role,name);
    }
}
