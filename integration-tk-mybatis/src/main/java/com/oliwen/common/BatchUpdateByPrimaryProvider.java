package com.oliwen.common;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

import java.util.Set;

public class BatchUpdateByPrimaryProvider extends MapperTemplate {
    private static final String TRIM = "</trim>";
    public static final String IF = "</if>";
    public static final String FOREACH = "</foreach>";

    public BatchUpdateByPrimaryProvider (Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String batchUpdateByPrimarySelective (MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        //开始拼sql
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
        sql.append("<trim prefix=\"set\" suffixOverrides=\",\">");

        //获取全部列
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);

        for (EntityColumn column : columnList) {
            if (!column.isId() && column.isUpdatable()) {
                sql.append("<trim prefix=\"").append(column.getColumn()).append(" =case\" suffix=\"end,\">");
                sql.append("<foreach collection=\"list\" item=\"record\" index=\"index\">");
                sql.append("<if test=\"record.").append(column.getEntityField().getName()).append("!=null\">");
                sql.append("when id=#{record.id} then #{record.").append(column.getEntityField().getName()).append("}");
                sql.append(IF);
                sql.append(FOREACH);
                sql.append(TRIM);
            }
        }
        sql.append(TRIM);
        sql.append("WHERE");
        sql.append(" id IN ");
        sql.append("<trim prefix=\"(\" suffix=\")\">");
        sql.append("<foreach collection=\"list\" separator=\", \" item=\"record\" index=\"index\" >");
        sql.append("#{record.id}");
        sql.append(FOREACH);
        sql.append(TRIM);

        return sql.toString();
    }

}
