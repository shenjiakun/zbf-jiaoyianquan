package com.zbf.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zbf.common.entity.my.BaseUserRole;
import com.zbf.user.mapper.BaseUserRoleMapper;
import com.zbf.user.service.IBaseUserRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author thyu
 * @since 2020-09-17
 */
@Service
public class BaseUserRoleServiceImpl extends ServiceImpl<BaseUserRoleMapper, BaseUserRole> implements IBaseUserRoleService {

}
