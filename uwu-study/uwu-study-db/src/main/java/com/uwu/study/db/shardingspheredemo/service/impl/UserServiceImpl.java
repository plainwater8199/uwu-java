package com.uwu.study.db.shardingspheredemo.service.impl;

import com.uwu.study.db.info.entity.UserDo;
import com.uwu.study.db.info.dao.UserDao;
import com.uwu.study.db.shardingspheredemo.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userMapper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createUser() {
        UserDo user = new UserDo();
        user.setUserId(Long.valueOf(randomID(10)));
        user.setName("water");
        user.setPhone(18100000001L);
        user.setMail("18100000001@qq.com");
        user.setCreator("system");
        user.setCreateTime(new Date());
        user.setUpdater("system");
        user.setUpdateTime(new Date());
        userMapper.insert(user);

//        throw new RuntimeException();


    }

    public static String randomID(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();

    }
}
