package com.zbf.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zbf.common.entity.my.BaseUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author thyu
 * @since 2020-09-11
 */
public interface BaseUserMapper extends BaseMapper<BaseUser> {

    IPage<BaseUser> selectPageVo(Page<Object> page, BaseUser vo);

    @Insert("insert into base_user(userName,loginName,sex,tel,email,passWord,salt) values(#{userName},#{loginName},#{sex},#{tel},#{email},#{passWord},#{salt})")
    Boolean getUserAdd(BaseUser user);
}
