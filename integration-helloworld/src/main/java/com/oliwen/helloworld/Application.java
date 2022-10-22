package com.oliwen.helloworld;


import cn.hutool.core.util.StrUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 启动类
 * @author olw
 * @since 2022/10/18 23:53
 */
@SpringBootApplication
@RestController
public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping("/sayHello")
    public String sayHello(@RequestParam(required = false, name = "who") String who) {
        if (StrUtil.isBlank(who)) {
            who = "World";
        }
        return StrUtil.format("Hello, {}!", who);
    }

}
