package com.oliwen.test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oliwen.mapper.MybatisUserMapper;
import com.oliwen.pojo.MybatisUser;
import org.apache.ibatis.annotations.Select;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
public class TestMapper {

    @Autowired
    MybatisUserMapper mybatisUserMapper;

    @Test
    public void testSelect (){
        List<MybatisUser> mybatisUsers = mybatisUserMapper.selectList(new QueryWrapper<>());
        System.out.println(mybatisUsers);
    }

    @Test
    public void  testInsert (){
        int row = mybatisUserMapper.insert(
            MybatisUser.builder().name("张三").password("123").salt("123").status(1).phoneNumber("123")
                .email("123@qq.com").createTime(new Date()).build());
        System.out.println(row);
    }

    @Test
    public void  testInsertXml (){
        int row = mybatisUserMapper.saveUser(
            MybatisUser.builder().name("张三").password("123").salt("123").status(1).phoneNumber("123")
                .email("123@qq.com").createTime(new Date()).build());
        System.out.println(row);
    }

    @Test
    public void selectByName(){
        List<MybatisUser> mybatisUsers = mybatisUserMapper.selectList(new LambdaQueryWrapper<MybatisUser>().eq(MybatisUser::getName, "张三"));
        System.out.println(mybatisUsers);
    }

    @Test
    public void testPage (){
        // 分页需要开启分页插件配置 详情查看config 或官网地址
        Page<MybatisUser> page = new Page<>(1, 10);
        mybatisUserMapper.selectPage(page, new LambdaQueryWrapper<MybatisUser>().like(MybatisUser::getName, "test%"));

        System.out.println(page);
    }
}
