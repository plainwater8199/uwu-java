//package com.uwu.study.db.shardingsphere;
//
//import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
//import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
//
//import javax.xml.bind.ValidationException;
//import java.util.Collection;
//import java.util.Collections;
//
//public abstract class BasePreciseShardingAlgorithm<T extends Comparable<?>> extends BaseShardingAlgorithm implements PreciseShardingAlgorithm<T> {
//
//    /**
//     * sharding分库分表基础类，处理框架在发送查询请求时的表
//     *
//     * @param availableTargetNames
//     * @param shardingValue
//     * @return: java.lang.String
//     * @author: 幸福的小雨
//     * @time: 2023/6/12 10:09
//     */
//    @Override
//    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<T> shardingValue) {
//        final String tableName = doSharding(getLogicTable(), availableTargetNames, shardingValue);
//        try {
//            checkTable(Collections.singletonList(tableName));
//        } catch (ValidationException e) {
//            throw new RuntimeException(e);
//        }
//        return tableName;
//    }
//
//    /**
//     * 计算表名称
//     *
//     * @param availableTargetNames
//     * @param shardingValue
//     * @return: java.lang.String
//     * @author: 幸福的小雨
//     * @time: 2023/6/12 10:08
//     */
//    public abstract String doSharding(String logicTable, Collection<String> availableTargetNames, PreciseShardingValue<T> shardingValue);
//
//}
