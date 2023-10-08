package com.uwu.study.db.shardingspheredemo.algorithm;

import com.google.common.base.Strings;
import lombok.val;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.math.BigInteger;
import java.util.Collection;

public class WaterPreciseTablesShardingAlgorithm implements PreciseShardingAlgorithm<Long> {
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        final String logicTableName = preciseShardingValue.getLogicTableName();//customer
        Long userIdValue = preciseShardingValue.getValue();
        String userId = preciseShardingValue.getColumnName();//customer_Id
        //实现user_$->{user_id%2+1}
//        BigInteger shardingValueB = BigInteger.valueOf(userIdValue);
//        BigInteger resB = (shardingValueB.mod(new BigInteger("2")).add(new BigInteger("1")));
        String tableName = logicTableName+"_"+String.valueOf(userIdValue).substring(0,10);//916203183
        System.out.println(tableName);
//        String key = logicTableName+"_"+resB;
//        if(collection.contains(key)){
//            return key;
//        }
        if(tableName.length() >10){
            return tableName;
        }
        throw new UnsupportedOperationException("route "+tableName+" is not supported, please check your config!");
    }
}
