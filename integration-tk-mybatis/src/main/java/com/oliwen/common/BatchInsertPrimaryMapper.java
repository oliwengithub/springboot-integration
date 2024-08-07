package com.oliwen.common;

import com.oliwen.common.BatchInsertPrimaryProvider;
import com.oliwen.common.BatchInsertProvider;
import org.apache.ibatis.annotations.InsertProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;

import java.util.List;

/**
 * 批量插入通用接口
 * @author <a href="https://gitee.com/oliwengithub">oliwengithub</a>
 * @since 2023/6/3 15:49
 */
@SuppressWarnings("all")
@RegisterMapper
public interface BatchInsertPrimaryMapper<T> {

    @InsertProvider(type = BatchInsertPrimaryProvider.class,  method = "dynamicSQL")
    int batchInsertPrimary(List<T> list);
}
