package com.uwu.study.db.info.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.uwu.study.db.info.entity.CustomerDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface CustomerDao extends BaseMapper<CustomerDo> {
    /**
     * 统计表数目
     * @param tableName 表名称
     * @return 数量
     */
//    int countTables(String tableName);

    /**
     * 创建表
     * @param tableName 表名称
     */
    void createCustomerTable(@Param("tableName") String tableName);

}
