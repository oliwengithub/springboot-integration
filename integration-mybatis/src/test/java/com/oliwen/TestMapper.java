package com.oliwen;

import com.oliwen.mapper.MybatisMapper;
import com.oliwen.pojo.MybatisUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TestMapper {

    @Autowired
    MybatisMapper mybatisMapper;

    @Test
    public void testSelect (){
        List<MybatisUser> mybatisUsers = mybatisMapper.selectAllUser();
        System.out.println(mybatisUsers);
    }
}
