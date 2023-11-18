package com.uwu.study.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.uwu.study.db.info.dao.CustomerDao;
import com.uwu.study.db.info.dao.UserDao;
import com.uwu.study.db.info.entity.CustomerDo;
import com.uwu.study.db.info.entity.UserDo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CreateTableTest {

    @Resource
    private CustomerDao customerDao;

    @Resource
    private UserDao userDao;


//    @Resource
//    private StatisticMapper statisticMapper;




    @Test
    public void createCustomerTable(){
        customerDao.createCustomerTable("customer_water2");
    }

    @Test
    public void addUser(){
        for(int i = 0;i<10;i++){
            UserDo user = new UserDo();
            Long userId = Long.valueOf(randomID(10));
            user.setUserId(userId);
            user.setName("CSP"+i);
            user.setPhone(Long.valueOf("1810808080"+i));
            user.setMail("18118"+i+"@qq.com");
            user.setCreator("system");
            user.setCreateTime(new Date());
            user.setUpdater("system");
            user.setUpdateTime(new Date());
            userDao.insert(user);
            customerDao.createCustomerTable("customer_"+userId);
//            statisticMapper.createStatisticTable("customer_"+userId);

        }
    }


    public static String randomID(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    @Test
    public void addCustomer(){
        CustomerDo customer;
        for(int i = 0; i<100 ;i++){
            customer = new CustomerDo();
            String userId = getUserId();
            String randomId = randomID(5);
            customer.setCustomerId(Long.valueOf(userId+randomId));
            customer.setName("water"+randomId);
            customer.setScore(getScore());
            customerDao.insert(customer);
        }

    }

    private String getUserId() {
        List<String> userIds = new ArrayList<>();
        userIds.add("2795797879");
        userIds.add("4074863200");
        userIds.add("5269356437");
        userIds.add("6595915436");
        userIds.add("7128266136");
        userIds.add("8574870324");

        Random random = new Random();
        int key =  random.nextInt(5);
        return userIds.get(key);
    }

    private Integer getScore() {
        Random random = new Random();
        return  1 + random.nextInt(100-1+1);
    }

    @Test
    public void queryCustomer(){
        LambdaQueryWrapper<CustomerDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(CustomerDo::getCustomerId,288268783502310L,400653226019399L,404499323160452L);
        List<CustomerDo> customers = customerDao.selectList(queryWrapper);
        customers.forEach(i-> System.out.println(i));
    }


}
