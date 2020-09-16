package com.zbf.user.service.impl;

import com.zbf.common.entity.my.BaseUser;
import com.zbf.user.mapper.BaseUserMapper;
import com.zbf.user.service.IBaseUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 申嘉坤
 * @since 2020-09-11
 */
@Service
public class BaseUserServiceImpl extends ServiceImpl<BaseUserMapper, BaseUser> implements IBaseUserService {

}
