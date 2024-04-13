package com.oliwen.excel;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.StringUtils;
import com.oliwen.annotation.CustomerDict;
import com.oliwen.util.DictUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * excel 导入导出解析成 K-> value
 * 适用场景 如类型映射 导入是通过名称映射成code 导出通过code映射成名称
 * @author <a href="https://gitee.com/oliwengithub">oliwengithub</a>
 * @since 2024/4/13 14:55
 */
@Slf4j
public class StringStringExcelConverter implements Converter<String> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public WriteCellData<?> convertToExcelData(WriteConverterContext<String> context) {
        String value = context.getValue();
        if (StringUtils.isEmpty(value)) {
            return new WriteCellData<>(value);
        }
        ExcelContentProperty contentProperty = context.getContentProperty();
        if (contentProperty == null) {
            return new WriteCellData<>(value);
        }
        Field field = contentProperty.getField();
        if (!field.isAnnotationPresent(CustomerDict.class)) {
            return new WriteCellData<>(value);
        }
        return new WriteCellData<>(DictUtil.toLabel(field.getAnnotation(CustomerDict.class).value(), value));
    }

    @Override
    public String convertToJavaData(ReadConverterContext<?> context) {
        String excelValue = context.getReadCellData().getStringValue();
        if (StringUtils.isEmpty(excelValue)) {
            return excelValue;
        }
        ExcelContentProperty contentProperty = context.getContentProperty();
        if (contentProperty == null) {
            return excelValue;
        }
        Field field = contentProperty.getField();
        if (!field.isAnnotationPresent(CustomerDict.class)) {
            return excelValue;
        }
        return DictUtil.toValue(field.getAnnotation(CustomerDict.class).value(), excelValue);
    }
}
