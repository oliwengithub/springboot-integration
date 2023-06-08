package com.oliwen;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.oliwen.common.BatchInsertProvider;
import com.oliwen.mapper.MybatisUserMapper;
import com.oliwen.mapper.TkMybatisMapper;
import com.oliwen.pojo.MybatisUser;
import com.oliwen.pojo.TkMybatis;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class TestMapper {

    @Autowired
    MybatisUserMapper mybatisUserMapper;

    @Autowired
    TkMybatisMapper tkMybatisMapper;
    @Test
    public void test1(){
        List<MybatisUser> userList = new ArrayList<>(5000);
        for (int i = 15050; i < 15100; i++) {
            userList.add(MybatisUser.builder().email("rtrrss1ts"+i+".com.cn").name("rt2221rrts"+i).password("rtr3rts"+i%17).salt("salt").phoneNumber("11ts" +
                    "one"+i).status(1).createTime(new Date()).build());
        }
        Date begin = new Date();


        mybatisUserMapper.batchInsert(userList);

        // 5000 1872ms
        System.out.println(String.format("目前耗时：%dms", new Date().getTime() - begin.getTime()));
    }

    @Test
    public void test2(){
        MybatisUser mybatisUser = MybatisUser.builder().email("rt111rrts221s.com.cn").name("rtrrts").password("rtrrts221s").salt("salt").phoneNumber("rtrrts0001").status(1).createTime(new Date()).build();
        mybatisUserMapper.insert(mybatisUser);
        System.out.println(mybatisUser);
    }
    @Test
    public void test3(){
        MybatisUser mybatisUser = MybatisUser.builder().email("sfsfssfssf.com.cn").name("sfsdfsdfssa").password("sfsfssfsfsfs").salt("salt").phoneNumber("sfsdsdfsffsfsd").status(1).createTime(new Date()).build();
        //mybatisUserMapper.batchInsertSelective(Collections.singletonList(mybatisUser));
        System.out.println(mybatisUser);
    }

    @Test
    public void test4(){
        TkMybatis tkMybatis = TkMybatis.builder().name("sfsdff").id("1").build();
        tkMybatisMapper.batchInsertPrimary(Collections.singletonList(tkMybatis));
        System.out.println(tkMybatis);
    }

    @Test
    public void test5(){
        TkMybatis tkMybatis = TkMybatis.builder().name("sfsdff").build();
        tkMybatisMapper.insert(tkMybatis);
        System.out.println(tkMybatis);
    }
    @Test
    public void test6(){
        List<TkMybatis> tkMybatis = tkMybatisMapper.selectList(new LambdaQueryWrapper<>());
        int i = tkMybatisMapper.batchUpdateByPrimarySelective(tkMybatis);
        System.out.println(i);
    }


}
