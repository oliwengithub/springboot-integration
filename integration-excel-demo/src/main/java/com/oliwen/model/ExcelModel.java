package com.oliwen.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

/**
 * excel 模型
 * @author <a href="https://gitee.com/oliwengithub">oliwengithub</a>
 * @since 2024/4/9 23:48
 */
@Data
public class ExcelModel {


    @ExcelProperty(value = "名称")
    @NotEmpty(message = "名称不能为空")
    private String username;

    @ExcelProperty(value = "年龄")
    private Integer age;
    @ExcelProperty(value = "颜值分数")
    private BigDecimal appearanceScore;
}
