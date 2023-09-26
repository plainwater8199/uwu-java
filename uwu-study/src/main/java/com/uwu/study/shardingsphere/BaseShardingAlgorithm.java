package com.uwu.study.shardingsphere;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.core.rule.TableRule;
import org.springframework.util.CollectionUtils;

import javax.xml.bind.ValidationException;
import java.util.*;

@Setter
@Getter
@Slf4j
public class BaseShardingAlgorithm {
    /**
     * 数据库内所有表
     */
    private Set<String> tables;

    /**
     * 数据节点名称
     */
    private String nodeName;

    /**
     * 逻辑表名
     */
    private String logicTable;

    /**
     * 表的权限缓存
     */
    private TableRule tableRule;

    /**
     * 自动配置数据节点类
     */
    private AutoConfigDataNodes autoConfigDataNodes;

    public BaseShardingAlgorithm() {
        this.autoConfigDataNodes = ApplicationContextUtils.getBean(AutoConfigDataNodes.class);
    }

    /**
     * 动态获取表，如果表不在数据库中在数据中则创建该表
     *
     * @param tableNames
     * @return: java.lang.String
     * @author: 幸福的小雨
     * @time: 2023/6/12 10:40
     */
    protected boolean checkTable(Collection<String> tableNames) throws ValidationException {
        if (CollectionUtils.isEmpty(tableNames)) {
            return false;
        }
        // 执行合法的业务流程
        if (tables == null) {
            autoConfigDataNodes.createTable(tableRule, nodeName, tableNames);
        }
        List<String> list = new ArrayList<>();
        for (String tableName : tableNames) {
            if (!CollectionUtils.containsAny(tables, Arrays.asList(tableName))) {
                list.add(tableName);
            }
        }
        autoConfigDataNodes.createTable(tableRule, nodeName, list);
        return true;
    }

    protected Set<String> getTables() {
        return tables;
    }

}
