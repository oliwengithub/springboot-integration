package com.oliwen;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oliwen.mapper.MybatisMapper;
import com.oliwen.pojo.MybatisUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
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

    @Test
    public void  testInsert (){
        for (int i = 0; i < 100000; i++) {
            mybatisMapper.saveUser(MybatisUser.builder().email("123@qq"+i+".com").name("test"+i).password("password"+i).salt("salt"+i).status(1).phoneNumber("188"+i).createTime(new Date()).build());
        }
    }

    @Test
    public void testPage (){
        PageHelper.startPage(1, 10);
        List<MybatisUser> mybatisUsers = mybatisMapper.selectAllUser();
        PageInfo<MybatisUser> pageInfo = new PageInfo<>(mybatisUsers);
        System.out.println(pageInfo);
    }
}
