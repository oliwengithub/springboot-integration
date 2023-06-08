package com.oliwen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oliwen.common.BatchInsertMapper;
import com.oliwen.pojo.MybatisUser;

/**
 * 用户表 mapper
 * @author olw
 * @since 2022/10/22 16:39
 */
public interface MybatisUserMapper extends BatchInsertMapper<MybatisUser>, BaseMapper<MybatisUser> {

}
