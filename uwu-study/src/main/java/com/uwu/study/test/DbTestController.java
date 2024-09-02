package com.uwu.study.test;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.uwu.study.test.dao.UwuOrderDao;
import com.uwu.study.test.entity.UwuOrderDo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jcrenc
 * @since 2024/2/28 9:32
 */

@RestController
public class DbTestController {

    @Resource
    private UwuOrderDao uwuOrderDao;

    @RequestMapping("test")
    public String test() {
        LambdaQueryWrapper<UwuOrderDo> queryWrapper = new LambdaQueryWrapper<>();
        List<UwuOrderDo> uwuOrderDos = uwuOrderDao.selectList(queryWrapper);
        for (UwuOrderDo uwuOrderDo : uwuOrderDos) {
            System.out.println(uwuOrderDo);
        }
        System.out.println("test");
        return "test";
    }

}
