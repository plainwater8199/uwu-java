package com.uwu.study.db.shardingspheredemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.uwu.study.db.shardingspheredemo.entity.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface CustomerMapper extends BaseMapper<Customer> {
    /**
     * 统计表数目
     * @param tableName 表名称
     * @return 数量
     */
    int countTables(String tableName);

    /**
     * 创建表
     * @param tableName 表名称
     */
    void createCustomerTable(@Param("tableName") String tableName);

}
