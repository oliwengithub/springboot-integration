package com.oliwen.controller;


import com.oliwen.excel.ExcelUtil;
import com.oliwen.model.ExcelModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 测试
 * @author <a href="https://gitee.com/oliwengithub">oliwengithub</a>
 * @since 2024/4/10 0:11
 */
@Slf4j
@RestController
public class ExcelTestController {

    @PostMapping("/importTest")
    public void importTest(MultipartFile file){
        ExcelUtil.read(file, ExcelModel.class, t->{
            log.info("处理解析出来的业务数据{}", t);
        });
    }
}
