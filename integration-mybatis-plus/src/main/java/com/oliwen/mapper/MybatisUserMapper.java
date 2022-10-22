package com.oliwen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oliwen.pojo.MybatisUser;
import org.apache.ibatis.annotations.Param;

/**
 * 用户表 mapper
 * @author olw
 * @since 2022/10/22 16:39
 */
public interface MybatisUserMapper extends BaseMapper<MybatisUser> {

    /**
     * xml 插入
     * @author olw
     * @since 2022/10/22 16:51
     * @param mybatisUser
     * @return int
    */
    int saveUser(@Param("user") MybatisUser mybatisUser);
}
