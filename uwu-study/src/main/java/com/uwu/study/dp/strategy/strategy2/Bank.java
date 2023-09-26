package com.uwu.study.dp.strategy.strategy2;

import lombok.Data;

@Data
public class Bank extends PayStrategy{
    private String cardNo;

    @Override
    void beforeExec() {
        System.out.println("------------------------银行-执行前--------------");
    }

    @Override
    void Exec() {
        System.out.println("银行卡："+getMoney());
        System.out.println("银行卡号："+cardNo);
    }

    @Override
    void afterExec() {
        System.out.println("------------------------银行-执行前--------------");
    }
}
