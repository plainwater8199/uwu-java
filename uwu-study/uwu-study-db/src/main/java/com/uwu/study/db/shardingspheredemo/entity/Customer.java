package com.uwu.study.db.shardingspheredemo.entity;

import lombok.Data;

@Data
public class Customer {
    private Long customerId;

    private String name;

    private Integer score;
}
