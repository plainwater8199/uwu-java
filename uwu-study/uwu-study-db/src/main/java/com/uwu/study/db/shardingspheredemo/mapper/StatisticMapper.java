package com.uwu.study.db.shardingspheredemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.uwu.study.db.shardingspheredemo.entity.Statistic;
import org.apache.ibatis.annotations.Param;

public interface StatisticMapper extends BaseMapper<Statistic> {
    /**
     * 创建表
     * @param tableName 表名称
     */
    void createStatisticTable(@Param("tableName") String tableName);
}
