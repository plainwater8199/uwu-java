package com.uwu.study.db.info.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("user")
public class UserDo {

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
