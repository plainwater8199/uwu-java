package com.uwu.study.water.db.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * @author jcrenc
 * @since 2025/1/15 9:57
 */
@Mapper
public interface MsgRecordDDLDao {
    @Update("ALTER TABLE ${tableName} ADD INDEX idx_account_type (`account_type`)")
    void addIndexForAccountType(@Param("tableName") String tableName);

    @Update("ALTER TABLE ${tableName} ADD INDEX idx_plan_detail_id (`plan_detail_id`)")
    void addIndexForPlanDetailId(@Param("tableName") String tableName);

    @Update("ALTER TABLE ${tableName} ADD INDEX idx_creator (`creator`)")
    void addIndexForCreator(@Param("tableName") String tableName);

    @Update("ALTER TABLE ${tableName} ADD INDEX idx_send_result (`send_result`)")
    void addIndexForSendResult(@Param("tableName") String tableName);

    @Update("ALTER TABLE ${tableName} ADD INDEX idx_phone_num (`phone_num`)")
    void addIndexForPhoneNum(@Param("tableName") String tableName);

    @Update("ALTER TABLE ${tableName} ADD INDEX idx_message_id (`message_id`)")
    void addIndexForMessageId(@Param("tableName") String tableName);

    @Update("ALTER TABLE ${tableName} ADD INDEX idx_account_id (`account_id`)")
    void addIndexForAccountId(@Param("tableName") String tableName);

    @Select("SHOW INDEX FROM ${tableName}")
    List<Map<String, Object>> getTableIndexes(@Param("tableName") String tableName);


    @Update("ALTER TABLE ${tableName} DROP INDEX ${indexName}")
    void dropIndex(@Param("tableName")String tableName, @Param("indexName")String indexName);


    @Update("ALTER TABLE ${tableName} ADD INDEX ${indexName} (`${index}`)")
    void addIndex(@Param("tableName") String tableName, @Param("indexName") String indexName, @Param("index")String index);
}
