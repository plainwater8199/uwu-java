package com.uwu.study.water.db.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@TableName("water_test")
public class WaterTest implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;


    @TableField("name")
    private String name;

    @TableField("msg_id")
    private String msgId;


    @TableField("type")
    private String type;


    @TableField("description")
    private String description;


    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    @TableField(value = "creator", fill = FieldFill.INSERT)
    private String creator;


    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(value = "updater", fill = FieldFill.INSERT_UPDATE)
    private String updater;

    @TableField("deleted")
    private Integer deleted;


}

