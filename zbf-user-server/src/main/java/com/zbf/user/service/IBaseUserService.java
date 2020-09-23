package com.zbf.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zbf.common.entity.my.BaseUser;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author thyu
 * @since 2020-09-11
 */
public interface IBaseUserService extends IService<BaseUser> {

    IPage<BaseUser> selectPageVo(Page<Object> page, BaseUser vo);


    Boolean getUserAdd(BaseUser user);
}
