package com.oliwen.common;

import com.oliwen.mapper.BatchInsertMapper;
import com.oliwen.pojo.MybatisUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

import java.util.Set;

public class BatchInsertProvider extends MapperTemplate {

    public BatchInsertProvider (Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String batchInsert(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        //开始拼sql
        StringBuilder sql = new StringBuilder();
        sql.append("<bind name=\"listNotEmptyCheck\" value=\"@tk.mybatis.mapper.util.OGNL@notEmptyCollectionCheck(list, '").append(ms.getId()).append(" 方法参数为空')\"/>");
        sql.append(SqlHelper.insertIntoTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.insertColumns(entityClass, false, false, false));
        sql.append(" VALUES ");
        sql.append("<foreach collection=\"list\" item=\"record\" separator=\",\" >");
        sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
        //获取全部列
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        for (EntityColumn column : columnList) {
            if (column.isInsertable()) {
                sql.append(column.getColumnHolder("record")).append(",");
            }
        }
        sql.append("</trim>");
        sql.append("</foreach>");

        // 反射把MappedStatement中的设置主键名
        EntityHelper.setKeyProperties(EntityHelper.getPKColumns(entityClass), ms);

        return sql.toString();
    }

    /**
     * 用户表 mapper
     * @author olw
     * @since 2022/10/22 16:39
     */
    public static interface MybatisUserMapper extends BatchInsertMapper<MybatisUser> {

        /**
         * xml 插入
         * @author olw
         * @since 2022/10/22 16:51
         * @param mybatisUser
         * @return int
        */
        int saveUser(@Param("user") MybatisUser mybatisUser);
    }
}
