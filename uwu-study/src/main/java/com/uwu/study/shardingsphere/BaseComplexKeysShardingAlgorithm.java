package com.uwu.study.shardingsphere;

import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

import javax.xml.bind.ValidationException;
import java.util.Collection;

public abstract class BaseComplexKeysShardingAlgorithm <T extends Comparable<?>> extends BaseShardingAlgorithm implements ComplexKeysShardingAlgorithm<T> {

    /**
     * sharding分库分表基础类，处理框架在发送查询请求时的表
     *
     * @param availableTargetNames
     * @param shardingValue
     * @return: java.lang.String
     * @author: 幸福的小雨
     * @time: 2023/6/12 10:09
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<T> shardingValue) {
        final Collection<String> tableNames = doSharding(getLogicTable(), availableTargetNames, shardingValue);
        try {
            checkTable(tableNames);
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
        return tableNames;
    }

    /**
     * 计算表名称
     *
     * @param availableTargetNames
     * @param shardingValue
     * @return: java.lang.String
     * @author: 幸福的小雨
     * @time: 2023/6/12 10:08
     */
    public abstract Collection<String> doSharding(String logicTable, Collection<String> availableTargetNames, ComplexKeysShardingValue<T> shardingValue);

}
