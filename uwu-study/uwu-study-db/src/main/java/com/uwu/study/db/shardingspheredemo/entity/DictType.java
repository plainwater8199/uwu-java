package com.uwu.study.db.shardingspheredemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("dict_type")
public class DictType {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String typeCode;
    private Integer typeStatus;
    private String creator;
    private Date createTime;
}
