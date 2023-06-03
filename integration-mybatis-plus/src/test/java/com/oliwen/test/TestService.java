package com.oliwen.test;

import com.oliwen.pojo.MybatisUser;
import com.oliwen.service.MybatisUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
public class TestService {

    @Autowired
    private MybatisUserService mybatisUserService;

    @Test
    public void test1(){
        List<MybatisUser> userList = new ArrayList<>(6000000);
        for (int i = 1; i < 6000000; i++) {
            userList.add(MybatisUser.builder().email("tes11u1t"+i+".com.cn").name("l1i111iu"+i).password("a11ue1r"+i%17).salt("salt").phoneNumber("o11phu" +
                    "one"+i).build());
        }
        Date begin = new Date();


        mybatisUserService.saveBatch(userList, 50000);

        // 10000 634594ms
        // 20000 643620ms
        System.out.println(String.format("目前耗时：%dms", new Date().getTime() - begin.getTime()));
    }
}
