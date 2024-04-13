package com.oliwen.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.util.FieldUtils;
import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.handler.context.SheetWriteHandlerContext;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;

import javax.validation.ConstraintViolation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * TODO
 * @author <a href="https://gitee.com/oliwengithub">oliwengithub</a>
 * @since 2024/4/9 23:22
 */
@Slf4j
@RequiredArgsConstructor
public class ExcelErrorFillHandler<T> implements SheetWriteHandler, RowWriteHandler {
    /**
     * 错误结果集
     */
    private final List<ExcelLineResult<T>> resultList;
    /**
     * 标题所在行, 从1开始
     */
    private final Integer titleLineNumber;

    /**
     * 结果列序号
     */
    private int resultColNum;

    /**
     * 默认导入成功的提示
     */
    private static final String SUCCESS_MSG = "导入成功";


    private static void setCellStyle(Cell cell, IndexedColors color) {
        Workbook workbook = cell.getSheet().getWorkbook();
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setColor(color.getIndex());
        style.setFont(font);
        cell.setCellStyle(style);
    }

    @Override
    public void afterSheetCreate(SheetWriteHandlerContext context) {
        SheetWriteHandler.super.afterSheetCreate(context);
    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        Sheet cachedSheet = writeSheetHolder.getCachedSheet();
        for (int i = 1; i <= cachedSheet.getLastRowNum() + 1; i++) {
            // 空白数据, 不做处理
            if (i < titleLineNumber) {
                continue;
            }
            Row row = cachedSheet.getRow(i - 1);
            // 标题行, 创建标题
            if (i == titleLineNumber) {
                this.resultColNum = row.getLastCellNum();
                Cell cell = row.createCell(row.getLastCellNum(), CellType.STRING);
                setCellStyle(cell, IndexedColors.BLACK);
                cell.setCellValue("导入结果");
                continue;
            }
            // 结果行
            Cell cell = row.createCell(this.resultColNum, CellType.STRING);
            String errMsg = convertErrMsg(resultList.get(i - titleLineNumber - 1));
            if (errMsg == null) {
                setCellStyle(cell, IndexedColors.GREEN);
                cell.setCellValue(SUCCESS_MSG);
                continue;
            }
            setCellStyle(cell, IndexedColors.RED);
            cell.setCellValue(errMsg);
        }
    }

    /**
     * 解析每行的错误信息
     *
     * @param result 读取结果
     * @return 错误信息
     */
    private String convertErrMsg(ExcelLineResult<T> result) {
        if (result.getBizError() != null) {
            return result.getBizError();
        }
        if (result.getViolation().isEmpty()) {
            return null;
        }
        return result.getViolation().stream().map(e-> getMessage(e))
                .collect(Collectors.joining(";\n"));
    }

    public  String getMessage(ConstraintViolation<T> constraintViolation) {
        String message = constraintViolation.getMessage();
        if (!message.contains("{fieldTitle}")) {
            return message;
        }
        String fieldTitle = "";
        Class<?> rootBeanClass = constraintViolation.getRootBeanClass();
        if (Objects.nonNull(rootBeanClass)) {
            Field field = FieldUtils
                    .getField(rootBeanClass, constraintViolation.getPropertyPath().toString(), true);
            ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
            if (Objects.nonNull(excelProperty) && excelProperty.value().length != 0) {
                fieldTitle = excelProperty.value()[0];
            }
        }
        return message.replace("{fieldTitle}", fieldTitle);
    }
}
