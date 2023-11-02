//package com.uwu.study.db.shardingspheredemo.algorithm;
//
//import org.apache.shardingsphere.api.sharding.hint.HintShardingAlgorithm;
//import org.apache.shardingsphere.api.sharding.hint.HintShardingValue;
//
//import java.util.Arrays;
//import java.util.Collection;
//
//public class WaterHintTablesShardingAlgorithm implements HintShardingAlgorithm<Integer> {
//    //select * from course
//    @Override
//    public Collection<String> doSharding(Collection<String> collection, HintShardingValue<Integer> hintShardingValue) {
//
//        String key  = hintShardingValue.getLogicTableName()+"_"+hintShardingValue.getValues().toArray()[0];//2
//        if(collection.contains(key)){
//            return Arrays.asList(key);
//        }
//        throw new UnsupportedOperationException("route "+key+" is not supported, please check your config!");
//    }
//}
