package com.uwu.study.shardingsphere;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.shardingsphere.api.sharding.ShardingAlgorithm;
import org.apache.shardingsphere.core.rule.MasterSlaveRule;
import org.apache.shardingsphere.core.rule.TableRule;
import org.apache.shardingsphere.core.strategy.route.ShardingStrategy;
import org.apache.shardingsphere.core.strategy.route.complex.ComplexShardingStrategy;
import org.apache.shardingsphere.core.strategy.route.hint.HintShardingStrategy;
import org.apache.shardingsphere.core.strategy.route.standard.StandardShardingStrategy;
import org.apache.shardingsphere.shardingjdbc.jdbc.adapter.AbstractDataSourceAdapter;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.context.ShardingRuntimeContext;
import org.apache.shardingsphere.underlying.common.rule.DataNode;
import org.springframework.util.CollectionUtils;

import javax.xml.bind.ValidationException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class AutoConfigDataNodes {
    private final AbstractDataSourceAdapter dataSource;

    private ShardingRuntimeContext runtimeContext;

    private ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
            new BasicThreadFactory.Builder().namingPattern("sharding-schedule-pool-%d").daemon(true).build());

    public AutoConfigDataNodes(AbstractDataSourceAdapter dataSource) {
        log.info("开启sharding分表配置任务");
        this.dataSource = dataSource;
        executorService.scheduleWithFixedDelay(() -> run(), 0, 10, TimeUnit.SECONDS);
    }

    public void run() {
        log.debug("进行自动更新sharding中物理表配置");
        ShardingRuntimeContext runtimeContext = getRuntimeContext();
        Collection<MasterSlaveRule> ruleCollection = runtimeContext.getRule().getMasterSlaveRules();
        MasterSlaveRule masterSlaveRule = ruleCollection.stream().findFirst().get();
        String nodeName = masterSlaveRule.getRuleConfiguration().getName();
        Set<String> tablesInDBSet = queryTables();

        List<TableRule> tableRuleList = (List<TableRule>) runtimeContext.getRule().getTableRules();
        for (TableRule tableRule : tableRuleList) {
            refreshTableRule(tableRule, nodeName, tablesInDBSet);
            refreshShardingAlgorithm(tableRule, nodeName);
        }
    }

    /**
     * 刷新sharding缓存
     *
     * @param tableRule
     * @param nodeName
     * @param tablesInDBSet
     * @return: void
     * @author: 幸福的小雨
     * @time: 2023/6/13 10:09
     */
    protected void refreshTableRule(TableRule tableRule, String nodeName, Set<String> tablesInDBSet) {
        // sharding缓存的表名，目前不知道哪里再用，先当缓存吧
        Set<String> actualTableSets = getActualTables(tableRule);
        // 刷新分库分表时的缓存
        List<String> newList = matchNewList(actualTableSets, tablesInDBSet);
        setDatasourceToTablesMap(tableRule, nodeName, newList);
    }

    /**
     * 刷新分片算法内的属性
     *
     * @param tableRule
     * @param nodeName
     * @return: void
     * @author: 幸福的小雨
     * @time: 2023/6/13 11:33
     */
    protected void refreshShardingAlgorithm(TableRule tableRule, String nodeName) {
        // 获取分库分表时真正使用的表名
        Map<String, Set<String>> datasourceToTablesMap = getDatasourceToTablesMap(tableRule);
        Set<String> tables = datasourceToTablesMap.get(nodeName);
        ShardingStrategy shardingStrategy = tableRule.getTableShardingStrategy();
        if (shardingStrategy instanceof ComplexShardingStrategy) {
            ShardingAlgorithm algorithm = getObjectField(shardingStrategy, "shardingAlgorithm");
            setValueToBaseAlgorithm(tableRule, algorithm, nodeName, tables);
        } else if (shardingStrategy instanceof HintShardingStrategy) {
            ShardingAlgorithm algorithm = getObjectField(shardingStrategy, "shardingAlgorithm");
            setValueToBaseAlgorithm(tableRule, algorithm, nodeName, tables);
        } else if (shardingStrategy instanceof StandardShardingStrategy) {
            ShardingAlgorithm preciseAlgorithm = getObjectField(shardingStrategy, "preciseShardingAlgorithm");
            setValueToBaseAlgorithm(tableRule, preciseAlgorithm, nodeName, tables);
            ShardingAlgorithm rangeAlgorithm = getObjectField(shardingStrategy, "rangeShardingAlgorithm");
            setValueToBaseAlgorithm(tableRule, rangeAlgorithm, nodeName, tables);
        }
    }

    /**
     * 向基础类中设备必要的参数
     *
     * @param algorithm
     * @param tables
     * @return: void
     * @author: Shuai.Zhang 210744334
     * @time: 2023/6/13 13:27
     */
    private void setValueToBaseAlgorithm(TableRule tableRule, ShardingAlgorithm algorithm, String nodeName, Set<String> tables) {
        if (algorithm != null && algorithm instanceof BaseShardingAlgorithm) {
            BaseShardingAlgorithm baseShardingAlgorithm = (BaseShardingAlgorithm) algorithm;
            baseShardingAlgorithm.setLogicTable(tableRule.getLogicTable());
            baseShardingAlgorithm.setTables(tables);
            baseShardingAlgorithm.setTableRule(tableRule);
            baseShardingAlgorithm.setNodeName(nodeName);
        }
    }

    private List<String> matchNewList(Set<String> set1, Set<String> set2) {
        List<String> newList = new ArrayList<>();
        for (String s1 : set1) {
            for (String s2 : set2) {
                if (StringUtils.equals(s1, s2)) {
                    newList.add(s2);
                }
            }
        }
        return newList;
    }

    /**
     * 从存储中查询出所有的表
     *
     * @return: java.util.Set<java.lang.String>
     * @author: 幸福的小雨
     * @time: 2023/6/13 13:25
     */
    protected Set<String> queryTables() {
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;
        Set<String> tables = null;
        try {
            conn = dataSource.getConnection();
            statement = conn.createStatement();
            rs = statement.executeQuery("select tablename from pg_tables t where t.schemaname = 'public'");
            tables = new LinkedHashSet<>();
            while (rs.next()) {
                tables.add(rs.getString(1));
            }
        } catch (SQLException e) {
            log.error("获取数据库连接失败！", e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                log.error("关闭数据连接失败", e);
            }
        }
        return tables;
    }

    protected boolean createTable(TableRule tableRule, String nodeName, Collection<String> tableNames) throws ValidationException {
        if (CollectionUtils.isEmpty(tableNames)) {
            return false;
        }
        // 检查表的合法性
        Set<String> tableSets = getActualTables(tableRule);
        for (String tableName : tableNames) {
            if (!CollectionUtils.containsAny(tableSets, Arrays.asList(tableName))) {
                final String exec = MessageFormat.format("[{0}]表名称不合法", tableNames);
                throw new ValidationException(exec);
            }
        }

        // 以下为创建表和刷新缓存
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            conn = dataSource.getConnection();
            List<String> list = new LinkedList<>();
            for (String tableName : tableNames) {
                try {
                    final String sql = MessageFormat.format("CREATE TABLE {0} (LIKE {1})", tableName, tableRule.getLogicTable());
                    statement = conn.prepareStatement(sql);
                    int rs = statement.executeUpdate();
                    log.info("创建[{}]表, 状态 : {}", tableName, rs);
                } catch (SQLException e) {
                    if ("42P07".equals(e.getSQLState())) {
                        log.warn("数据库中存在{}表，程序将自动加入到缓存中。", tableNames);
                    } else {
                        throw e;
                    }
                }
                list.add(tableName);
            }
            addDatasourceToTablesMap(tableRule, nodeName, list);
            Set<String> tables = getDatasourceToTablesMap(tableRule).get(nodeName);
            refreshTableRule(tableRule, nodeName, tables);
            return true;
        } catch (SQLException e) {
            log.error("获取数据库连接失败！", e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                log.error("关闭数据连接失败", e);
            }
        }

        return false;
    }

    protected static <T> T getObjectField(Object object, String fieldName) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return (T) field.get(object);
        } catch (NoSuchFieldException e) {
            log.error("因为sharding版本问题", e);
        } catch (IllegalAccessException e) {
            log.error("因为sharding版本问题", e);
        }
        return null;
    }

    /**
     * 获取sharing运行时的上下文
     * 这个方法太坑了，项目启动时不能在主线程中使用，会产生循环依赖问题（试了各种办法），最后神奇的是放到子线程里就OK了
     *
     * @return: org.apache.shardingsphere.shardingjdbc.jdbc.core.context.ShardingRuntimeContext
     * @author: 幸福的小雨
     * @time: 2023/6/13 11:34
     */
    protected ShardingRuntimeContext getRuntimeContext() {
        try {
            if (runtimeContext == null) {
                Method getRuntimeContextMethod = dataSource.getClass().getDeclaredMethod("getRuntimeContext");
                getRuntimeContextMethod.setAccessible(true);
                runtimeContext = (ShardingRuntimeContext) getRuntimeContextMethod.invoke(dataSource, null);
            }
        } catch (IllegalAccessException e) {
            log.error("因为sharding版本问题", e);
        } catch (InvocationTargetException e) {
            log.error("因为sharding版本问题", e);
        } catch (NoSuchMethodException e) {
            log.error("因为sharding版本问题", e);
        }
        return runtimeContext;
    }

    protected Set<String> getActualTables(TableRule tableRule) {
        Set<String> tables = getObjectField(tableRule, "actualTables");
        return tables == null ? new LinkedHashSet<>() : tables;
    }

    protected Map<DataNode, Integer> getDataNodeIndexMap(TableRule tableRule) {
        Map<DataNode, Integer> nodeMap = getObjectField(tableRule, "dataNodeIndexMap");
        return nodeMap == null ? new HashMap<>(0) : nodeMap;
    }

    protected Map<String, Set<String>> getDatasourceToTablesMap(TableRule tableRule) {
        Map<String, Set<String>> tablesMap = getObjectField(tableRule, "datasourceToTablesMap");
        return tablesMap == null ? new HashMap<>(0) : tablesMap;
    }

    protected void setDatasourceToTablesMap(TableRule tableRule, String nodeName, List<String> newTableList) {
        synchronized (tableRule) {
            // 获取分库分表时真正使用的表名
            Map<String, Set<String>> datasourceToTablesMap = getDatasourceToTablesMap(tableRule);
            Set<String> tables = datasourceToTablesMap.get(nodeName);
            Collections.sort(newTableList);
            tables.clear();
            tables.addAll(newTableList);
        }
    }

    protected void addDatasourceToTablesMap(TableRule tableRule, String nodeName, List<String> tablesList) {
        List<String> list = new ArrayList<>();
        synchronized (tableRule) {
            // 获取分库分表时真正使用的表名
            Map<String, Set<String>> datasourceToTablesMap = getDatasourceToTablesMap(tableRule);
            Set<String> tables = datasourceToTablesMap.get(nodeName);
            list.addAll(tables);
            list.addAll(tablesList);
            Collections.sort(list);
            tables.clear();
            tables.addAll(list);
        }
    }
}
