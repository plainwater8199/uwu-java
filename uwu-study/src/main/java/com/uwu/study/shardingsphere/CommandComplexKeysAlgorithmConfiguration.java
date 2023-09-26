package com.uwu.study.shardingsphere;

import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CommandComplexKeysAlgorithmConfiguration extends BaseComplexKeysShardingAlgorithm<String> {

    @Override
    public Collection<String> doSharding(String logicTable, Collection<String> availableTargetNames, ComplexKeysShardingValue<String> shardingValue) {
        List<String> list = new ArrayList<>();
        Collection<String> guidValList = shardingValue.getColumnNameAndShardingValuesMap().get("guid");
        guidValList.forEach(id -> list.add(logicTable + "_" + id));
        return list;
    }
}
