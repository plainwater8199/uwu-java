package com.uwu.study.db.shardingspheredemo.algorithm;

import lombok.val;
import org.apache.shardingsphere.api.sharding.ShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.Arrays;
import java.util.Collection;

/**
 * RangeShardingAlgorithm<Long> Long 是传值
 */
public class WaterRangeTablesShardingAlgorithm implements RangeShardingAlgorithm<Long> {
    /**
     *
     * @param collection 可用的那些表
     * @param rangeShardingValue  分片
     * @return
     */
    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Long> rangeShardingValue) {
        Long upperEndpoint = rangeShardingValue.getValueRange().upperEndpoint();
        Long endpoint = rangeShardingValue.getValueRange().lowerEndpoint();
        String logicTableName = rangeShardingValue.getLogicTableName();

        return Arrays.asList(logicTableName+"_1",logicTableName+"_2");
    }
}
