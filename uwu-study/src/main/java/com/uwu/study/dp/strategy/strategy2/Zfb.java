package com.uwu.study.dp.strategy.strategy2;

import lombok.Data;

@Data
public class Zfb extends PayStrategy{
    private String address;


    @Override
    void beforeExec() {
        System.out.println("------------------------支付宝-执行前--------------");
    }

    @Override
    void Exec() {
        System.out.println("支付宝:"+getMoney());
        System.out.println("地址："+address);
    }

    @Override
    void afterExec() {
        System.out.println("------------------------支付宝-执行后--------------");
    }
}
