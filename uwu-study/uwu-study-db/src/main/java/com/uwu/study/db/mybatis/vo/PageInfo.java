package com.uwu.study.db.mybatis.vo;

import lombok.Data;

@Data
public class PageInfo {

    /**
     * 当前页数，从1开始
     */
    private Integer current;

    /**
     * 每页记录数
     */
    private Integer size;
}
