package com.oliwen;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * tk 整合
 * @author <a href="https://gitee.com/oliwengithub">oliwengithub</a>
 * @since 2023/6/5 22:42
 */
@SpringBootApplication
@MapperScan("com.oliwen.mapper")
public class TkMybatisApplication {
    public static void main (String[] args) {
        SpringApplication.run(TkMybatisApplication.class, args);
    }
}
