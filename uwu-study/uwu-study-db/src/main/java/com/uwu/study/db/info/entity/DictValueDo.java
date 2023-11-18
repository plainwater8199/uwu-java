package com.uwu.study.db.info.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("dict_value")
public class DictValueDo {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String typeCode;
    private String valueCode;
    private String value;
    private Integer sort;
    private Integer valueStatus;
    private String creator;
    private Date createTime;
}
