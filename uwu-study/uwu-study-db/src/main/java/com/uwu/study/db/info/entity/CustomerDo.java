package com.uwu.study.db.info.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("customer")
public class CustomerDo {
    private Long customerId;

    private String name;

    private Integer score;
}
