package com.oliwen.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oliwen.mapper.MybatisUserMapper;
import com.oliwen.pojo.MybatisUser;
import com.oliwen.service.MybatisUserService;
import org.springframework.stereotype.Service;

@Service
public class MybatisUserServiceImpl extends ServiceImpl<MybatisUserMapper, MybatisUser> implements MybatisUserService {
}
