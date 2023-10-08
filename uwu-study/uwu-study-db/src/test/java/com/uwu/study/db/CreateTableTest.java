package com.uwu.study.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.uwu.study.db.shardingspheredemo.entity.User;
import com.uwu.study.db.shardingspheredemo.entity.Customer;
import com.uwu.study.db.shardingspheredemo.mapper.CustomerMapper;
import com.uwu.study.db.shardingspheredemo.mapper.StatisticMapper;
import com.uwu.study.db.shardingspheredemo.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CreateTableTest {

    @Resource
    private CustomerMapper userCustomerMapper;

    @Resource
    private UserMapper userMapper;


    @Resource
    private StatisticMapper statisticMapper;




    @Test
    public void createCustomerTable(){
        userCustomerMapper.createCustomerTable("customer_water2");
    }

    @Test
    public void addUser(){
        for(int i = 0;i<10;i++){
            User user = new User();
            Long userId = Long.valueOf(randomID(10));
            user.setUserId(userId);
            user.setName("CSP"+i);
            user.setPhone(Long.valueOf("1810808080"+i));
            user.setMail("18118"+i+"@qq.com");
            user.setCreator("system");
            user.setCreateTime(new Date());
            user.setUpdater("system");
            user.setUpdateTime(new Date());
            userMapper.insert(user);
            userCustomerMapper.createCustomerTable("customer_"+userId);
            statisticMapper.createStatisticTable("customer_"+userId);

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
        Customer customer2 = new Customer();
        customer2.setCustomerId(Long.valueOf("4972636240"+randomID(5)));
        customer2.setName("water-4972636240");
        customer2.setScore(12);
        userCustomerMapper.insert(customer2);

        Customer customer3 = new Customer();
        customer3.setCustomerId(Long.valueOf("4044993231"+randomID(5)));
        customer3.setName("water-4044993231");
        customer3.setScore(33);
        userCustomerMapper.insert(customer3);

        Customer customer4 = new Customer();
        customer4.setCustomerId(Long.valueOf("1071282929"+randomID(5)));
        customer4.setName("water-1071282929");
        customer4.setScore(10);
        userCustomerMapper.insert(customer4);

        Customer customer5 = new Customer();
        customer5.setCustomerId(Long.valueOf("1582710551"+randomID(5)));
        customer5.setName("water-1582710551");
        customer5.setScore(10);
        userCustomerMapper.insert(customer5);
    }

    @Test
    public void queryCustomer(){
        LambdaQueryWrapper<Customer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Customer::getCustomerId,916203183055294L,107128292906734L,404499323160452L);
        List<Customer> customers = userCustomerMapper.selectList(queryWrapper);
        customers.forEach(i-> System.out.println(i));
    }


}
