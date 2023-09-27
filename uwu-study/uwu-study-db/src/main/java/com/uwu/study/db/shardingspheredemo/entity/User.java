package com.uwu.study.db.shardingspheredemo.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {

    private Long userId;

    private String name;

    private Long phone;

    private String mail;

    private String creator;

    private Date createTime;

    private String updater;

    private Date updateTime;

    private Integer deleted;

    private Long deletedTime;
}
