package com.uwu.study.db.mybatis.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.uwu.study.db.mybatis.vo.Customer;
import com.uwu.study.db.mybatis.vo.PageInfo;

public interface MybatisService {
    Page<Customer> pageTest(PageInfo pageInfo);
}
