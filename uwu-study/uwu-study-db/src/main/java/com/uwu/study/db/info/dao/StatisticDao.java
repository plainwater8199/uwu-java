package com.uwu.study.db.info.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.uwu.study.db.info.entity.StatisticDo;
import org.apache.ibatis.annotations.Param;

public interface StatisticDao extends BaseMapper<StatisticDo> {
    /**
     * 创建表
     * @param tableName 表名称
     */
    void createStatisticTable(@Param("tableName") String tableName);
}
