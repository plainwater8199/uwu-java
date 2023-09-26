package com.uwu.study.dp.strategy.strategy2;

import lombok.Data;

@Data
public abstract class PayStrategy {

    private String name;

    private String money;
    abstract void beforeExec();

    abstract void Exec();

    abstract void afterExec();

}
