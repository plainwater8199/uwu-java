package com.uwu.study.test.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("uwu_user")
public class UwuOrderDo {

    private Long id;

    private String name;

    private String role;

    private Long orderAmount;

    private Integer status;

    private String creator;

    private Date createTime;

    private String updater;

    private Date updateTime;

    private Integer deleted;

    private Date deletedTime;

}
