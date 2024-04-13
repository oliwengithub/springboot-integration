package com.oliwen.excel;


import com.alibaba.excel.EasyExcel;
import com.oliwen.util.RequestContextUtil;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * excel工具
 *
 * @author ly-chn
 */
@Slf4j
public class ExcelUtil {
    public static final String SUFFIX = ".xlsx";

    /**
     * 导出到Excel, 并自动完成后续下载操作
     *
     * @param filename  文件名称, 不必填写后缀
     * @param sheetName sheet页名称
     * @param pojoClass 对应java类
     * @param dataset   数据集
     */
    public static void write (String filename, String sheetName, Class<?> pojoClass, Collection<?> dataset) {
        HttpServletResponse response = RequestContextUtil.getResponse();
        try {
            setDownloadHeader(response, filename);
            EasyExcel.write(response.getOutputStream(), pojoClass).sheet(sheetName)
                    .registerConverter(new StringStringExcelConverter())
                    .doWrite(dataset);
        } catch (IOException e) {
            throw new RuntimeException("文件导出失败", e);
        }
    }


    /**
     * 导入, 标题行默认为1
     *
     * @param file      文件
     * @param pojoClass 实体类
     * @param consumer  消费数据, 执行SQL逻辑或其他逻辑等等,
     *                  如果抛出异常, 则异常message将作为Excel导入失败原因
     *                  否则为未知异常导致导入失败
     * @param <T>       对应类型
     * @return
     */
    /**
     * 导入 标题行默认为1
     *
     * @param file      文件
     * @param pojoClass 接收excel 解析的实体
     * @param consumer  消费数据
     *                  可以执行业务额外等校验 不通过可以直接
     * @return java.util.List<T>
     * @author olw
     * @since 2024/4/10 0:14
     */
    public static <T> List<T> read (@NotNull MultipartFile file, @NotNull Class<T> pojoClass, @NotNull Consumer<T> consumer) {
        return read(file, pojoClass, consumer, 1);
    }

    /**
     * 导入
     *
     * @param file            文件
     * @param pojoClass       实体类
     * @param consumer        消费数据, 执行SQL逻辑或其他逻辑等等,
     *                        如果抛出LyException异常, 则异常message将作为Excel导入失败原因
     *                        否则为未知异常导致导入失败
     * @param titleLineNumber 标题所在行, 从1开始
     * @param <T>             对应类型
     * @return
     */
    public static <T> List<T> read (@NotNull MultipartFile file,
                                    @NotNull Class<T> pojoClass,
                                    @NotNull Consumer<T> consumer,
                                    @NotNull Integer titleLineNumber) {
        try {
            ExcelImportListener<T> listener = new ExcelImportListener<>(consumer);
            @Cleanup InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, pojoClass, listener)
                    .headRowNumber(titleLineNumber)
                    .registerConverter(new StringStringExcelConverter())
                    .sheet().doRead();
            List<ExcelLineResult<T>> resultList = listener.getExcelLineResultList();
            boolean allSuccess = resultList.stream()
                    .allMatch(it -> it.getViolation().isEmpty() && Objects.isNull(it.getBizError()));
            if (allSuccess) {
                log.info("Excel 数据已全部导入: {}", resultList);
                return null;
            }
            log.error("Excel校验失败, 读取结果: {}", resultList);
            HttpServletResponse response = RequestContextUtil.getResponse();
            @Cleanup InputStream templateIs = file.getInputStream();
            setDownloadHeader(response, "文件导入失败.xlsx");
            EasyExcel.write(response.getOutputStream(), pojoClass)
                    .withTemplate(templateIs)
                    .autoCloseStream(false)
                    .registerWriteHandler(new ExcelErrorFillHandler<T>(resultList, titleLineNumber))
                    .needHead(false)
                    .sheet()
                    .doWrite(Collections.emptyList());
            return resultList.stream().map(ExcelLineResult::getTarget).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("文件读取失败", e);
            throw new RuntimeException("文件读取失败, 请检查文件格式");
        }

    }

    public static void setDownloadHeader (HttpServletResponse response, String filename) {
        if (!filename.endsWith(SUFFIX)) {
            filename += SUFFIX;
        }
        response.setCharacterEncoding("utf-8");
        filename = URLEncoder.encode(filename, StandardCharsets.UTF_8).replace("\\+", "%20");
        // axios下载时获取文件名
        response.setHeader("filename", filename);
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }
}
