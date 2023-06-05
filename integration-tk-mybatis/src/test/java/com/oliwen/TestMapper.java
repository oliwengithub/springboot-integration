package com.oliwen;

import com.oliwen.common.BatchInsertProvider;
import com.oliwen.mapper.MybatisUserMapper;
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
    MybatisUserMapper mybatisUserMapper;
    @Test
    public void test1(){
        List<MybatisUser> userList = new ArrayList<>(5000);
        for (int i = 1; i < 5000; i++) {
            userList.add(MybatisUser.builder().email("rtrrts"+i+".com.cn").name("rtrrts"+i).password("rtrrts"+i%17).salt("salt").phoneNumber("rtrrts" +
                    "one"+i).status(1).createTime(new Date()).build());
        }
        Date begin = new Date();


        mybatisUserMapper.batchInsert(userList);

        // 5000 1872ms
        System.out.println(String.format("目前耗时：%dms", new Date().getTime() - begin.getTime()));
    }

    @Test
    public void test2(){
        MybatisUser mybatisUser = MybatisUser.builder().email("rtrrts221s.com.cn").name("rtrrts").password("rtrrts221s").salt("salt").phoneNumber("rtrrts0001").status(1).createTime(new Date()).build();
        mybatisUserMapper.insert(mybatisUser);
        System.out.println(mybatisUser);
    }


}
