package com.oliwen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oliwen.common.BatchInsertMapper;
import com.oliwen.common.BatchInsertPrimaryMapper;
import com.oliwen.common.BatchUpdateByPrimaryMapper;
import com.oliwen.pojo.TkMybatis;

public interface TkMybatisMapper extends BaseMapper<TkMybatis>, BatchInsertMapper<TkMybatis>, BatchInsertPrimaryMapper<TkMybatis>, BatchUpdateByPrimaryMapper<TkMybatis> {
}
