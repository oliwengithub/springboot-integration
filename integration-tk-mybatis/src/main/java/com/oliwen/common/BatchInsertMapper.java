package com.oliwen.common;

import com.oliwen.common.BatchInsertProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import tk.mybatis.mapper.annotation.RegisterMapper;

import java.util.List;

/**
 * 批量插入通用接口
 * @author <a href="https://gitee.com/oliwengithub">oliwengithub</a>
 * @since 2023/6/3 15:49
 */
@SuppressWarnings("all")
@RegisterMapper
public interface BatchInsertMapper<T> {

    @Options(useGeneratedKeys = true)
    @InsertProvider(type = BatchInsertProvider.class,  method = "dynamicSQL")
    int batchInsert(List<T> list);

}
