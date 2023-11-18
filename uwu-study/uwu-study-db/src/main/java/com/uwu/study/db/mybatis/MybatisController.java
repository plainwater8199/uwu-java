package com.uwu.study.db.mybatis;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.uwu.study.db.mybatis.service.MybatisService;
import com.uwu.study.db.mybatis.vo.Customer;
import com.uwu.study.db.mybatis.vo.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@Slf4j
public class MybatisController {

    @Autowired
    private MybatisService mybatisService;

    @RequestMapping(value = "/pageTest")
    public Page<Customer> pageTest(@RequestBody PageInfo pageInfo){
        return mybatisService.pageTest(pageInfo);
    }
}
