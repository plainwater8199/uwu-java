package com.uwu.study.dp.strategy.strategy2;

import java.util.ArrayList;
import java.util.List;

public class StrategyTest {
    public static void main(String[] args) {
        List<PayStrategy> payStrategyList = new ArrayList<>();
        Zfb zfb = new Zfb();
        zfb.setAddress("中关村");
        zfb.setMoney("10000");
        zfb.setName("支付宝");
        payStrategyList.add(zfb);

        Wechat wechat = new Wechat();
        wechat.setWechatName("water");
        wechat.setMoney("10");
        wechat.setName("微信");
        payStrategyList.add(wechat);

        Bank bank = new Bank();
        bank.setCardNo("1234567890");
        bank.setMoney("100000000000000");
        bank.setName("招商");
        payStrategyList.add(bank);
        Context context;
        for(PayStrategy item : payStrategyList){
            context =  new Context(item);
            context.exec();
        }

    }
}
