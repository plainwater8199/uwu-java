//package com.uwu.study.db.shardingspheredemo.algorithm;
//
//import com.google.common.collect.Range;
//import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
//import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;
//
//import java.math.BigInteger;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//public class WaterComplexTablesShardingAlgorithm implements ComplexKeysShardingAlgorithm<Long> {
//    @Override
//    public Collection<String> doSharding(Collection<String> collection, ComplexKeysShardingValue<Long> complexKeysShardingValue) {
//        Range<Long> userIdRange = complexKeysShardingValue.getColumnNameAndRangeValuesMap().get("user_id");
//        Collection<Long> phoneCol = complexKeysShardingValue.getColumnNameAndShardingValuesMap().get("phone");
//
//        Long userIdUpper = userIdRange.upperEndpoint();
//        Long userIdLower = userIdRange.lowerEndpoint();
//
//        List<String> result = new ArrayList<>();
//        for(Long phone : phoneCol){
//            BigInteger phoneB = BigInteger.valueOf(phone);
//            BigInteger target = (phoneB.mod(new BigInteger("2"))).add(new BigInteger("1"));
//            result.add(complexKeysShardingValue.getLogicTableName()+"_"+target);
//        }
//        return result;
//    }
//}
