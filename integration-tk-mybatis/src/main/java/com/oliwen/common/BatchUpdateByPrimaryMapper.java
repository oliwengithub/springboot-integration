package com.oliwen.common;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;

import java.util.List;

@SuppressWarnings("all")
@RegisterMapper
public interface BatchUpdateByPrimaryMapper<T> {

    @UpdateProvider(type = BatchUpdateByPrimaryProvider.class,  method = "dynamicSQL")
    int batchUpdateByPrimarySelective(List<T> records);
}
