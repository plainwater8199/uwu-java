package com.uwu.study.water.db.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.uwu.study.water.db.entity.WaterTest;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface WaterTestMapper extends BaseMapper<WaterTest> {

    @Select("SHOW INDEX FROM water_test")
    List<Map<String, Object>> obtainIndex();

    @Update("ALTER TABLE water_test ADD INDEX name (`name`)")
    void addIndexForName();

    @Update("ALTER TABLE water_test ADD INDEX msg_id (`msg_id`)")
    void addIndexForMsgId();

    @Update("ALTER TABLE water_test DROP INDEX ${indexName}")
    void dropIndex(@Param("indexName") String indexName);
}
