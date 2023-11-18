package com.uwu.study.db.mybatis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.uwu.study.db.info.dao.CustomerDao;
import com.uwu.study.db.info.entity.CustomerDo;
import com.uwu.study.db.mybatis.service.MybatisService;
import com.uwu.study.db.mybatis.vo.Customer;
import com.uwu.study.db.mybatis.vo.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MybatisServiceImpl implements MybatisService {

    @Autowired
    private CustomerDao customerDao;
    @Override
    public Page<Customer> pageTest(PageInfo pageInfo) {
        Page<Customer> response = new Page<>();
        List<Customer> customers = new ArrayList<>();
        Page<CustomerDo> page = new Page<>(pageInfo.getCurrent(),pageInfo.getSize());
        LambdaQueryWrapper<CustomerDo> queryWrapper = new LambdaQueryWrapper<>();

        Page<CustomerDo> resultPage = customerDao.selectPage(page, queryWrapper);
        List<CustomerDo> records = resultPage.getRecords();
        for(CustomerDo item : records){
            Customer customer = new Customer();
            BeanUtils.copyProperties(item,customer);
            customers.add(customer);
        }
        response.setPages(resultPage.getPages());
        response.setTotal(resultPage.getTotal());
        response.setSize(resultPage.getSize());
        response.setRecords(customers);


        return response;
    }
}
