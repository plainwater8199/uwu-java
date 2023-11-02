//package com.uwu.study.db.shardingspheredemo.algorithm;
//
//import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
//import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;
//
//import java.util.Arrays;
//import java.util.Collection;
//
//public class WaterRangeDBShardingAlgorithm implements RangeShardingAlgorithm<Long> {
//    @Override
//    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Long> rangeShardingValue) {
//        Long upperEndpoint = rangeShardingValue.getValueRange().upperEndpoint();
//        Long endpoint = rangeShardingValue.getValueRange().lowerEndpoint();
//        String logicTableName = rangeShardingValue.getLogicTableName();
//        //m$->{cid%2+1} m1,m2
//        return Arrays.asList("m1","m2");
//    }
//}
