package com.uwu.study.db.shardingspheredemo.algorithm;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.math.BigInteger;
import java.util.Collection;

public class WaterPreciseDBShardingAlgorithm  implements PreciseShardingAlgorithm<Long> {
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        final String logicTableName = preciseShardingValue.getLogicTableName();
        Long userIdValue = preciseShardingValue.getValue();
        String userId = preciseShardingValue.getColumnName();
        //实现user_$->{user_id%2+1}
        BigInteger shardingValueB = BigInteger.valueOf(userIdValue);
        BigInteger resB = (shardingValueB.mod(new BigInteger("2")).add(new BigInteger("1")));
        String key = "m"+resB;
        if(collection.contains(key)){
            return key;
        }
        throw new UnsupportedOperationException("route "+key+" is not supported, please check your config!");
    }
}
