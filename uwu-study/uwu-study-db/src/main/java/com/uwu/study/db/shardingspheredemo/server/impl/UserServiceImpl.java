package com.uwu.study.db.shardingspheredemo.server.impl;

import com.uwu.study.db.shardingspheredemo.entity.User;
import com.uwu.study.db.shardingspheredemo.mapper.UserMapper;
import com.uwu.study.db.shardingspheredemo.server.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Override
    public void createUser() {
        User user = new User();
        user.setName("water");
        user.setPhone(18100000000L);
        user.setMail("18100000000@qq.com");
        user.setCreator("system");
        user.setCreateTime(new Date());
        user.setUpdater("system");
        user.setUpdateTime(new Date());
        userMapper.insert(user);


    }
}
