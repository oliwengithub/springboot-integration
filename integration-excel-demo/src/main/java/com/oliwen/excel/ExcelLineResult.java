package com.oliwen.excel;

import lombok.Builder;
import lombok.Data;

import javax.validation.ConstraintViolation;
import java.util.Set;


/**
 * Excel按行导入结果
 *
 * @author ly-chn
 */
@Data
@Builder
class ExcelLineResult<T> {

    /**
     * 行号, 从0开始
     */
    private Integer rowIndex;
    /**
     * 导入的数据
     */
    private T target;
    /**
     * 校验结果
     */
    private Set<ConstraintViolation<T>> violation;
    /**
     * 业务异常错误信息
     */
    private String bizError;
}
