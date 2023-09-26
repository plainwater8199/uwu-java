package com.uwu.study.dp.strategy.strategy2;

import lombok.Data;

@Data
public class Wechat extends PayStrategy{
    private String wechatName;


    @Override
    void beforeExec() {
        System.out.println("------------------------微信-执行前--------------");
    }

    @Override
    void Exec() {
        System.out.println("微信："+getMoney());
        System.out.println("微信名："+wechatName);
    }

    @Override
    void afterExec() {
        System.out.println("------------------------微信-执行后--------------");
    }
}
