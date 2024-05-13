package com.uwu.study.ddd.mvcdemo.dto;

import lombok.Data;

@Data
public class User {
    Long userId;
    String name;
    String phone;
    String address;
    Long repId;
}
