package com.uwu.study.db.shardingspheredemo;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.shardingsphere.core.rule.ShardingRule;
import org.apache.shardingsphere.core.rule.TableRule;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.apache.shardingsphere.underlying.common.rule.DataNode;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 应用在启动后需要执行一些操作，比如初始化配置、加载模板文件
 * 1、该实现类，要注入到spring容器中，这里使用了 @Component 注解；
 * 2、同一个项目中，可以定义多个 ApplicationRunner 的实现类；
 * 3、存在多个 ApplicationRunner 的实现类时，可通过 @Order 注解来确定执行顺序，数值越小（值可为负数），越先执行；
 * 4、run方法的参数，ApplicationArguments 可以获取到当前项目执行的命令参数（比如把这个项目打成jar执行的时候，带的参数可以通过 ApplicationArguments 获取到）；
 *
 */
@Component
public class NodesAutoConfigurator implements ApplicationRunner {

    @Resource
    private DataSource dataSource;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        autoConfigShardingNodes();
    }

    private void autoConfigShardingNodes() throws NoSuchFieldException, IllegalAccessException, SQLException {
        //sharding数据源
        ShardingDataSource shardingDataSource = (ShardingDataSource) this.dataSource;

        //所有的拆分表
        ShardingRule rule = shardingDataSource.getRuntimeContext().getRule();
        Collection<TableRule> tableRules = rule.getTableRules();

        for (TableRule tableRule : tableRules) {
            // 表的节点
            List<DataNode> dataNodes = tableRule.getActualDataNodes();
            // 动态刷新actualDataNodesField
            Field actualDataNodesField = TableRule.class.getDeclaredField("actualDataNodes");
//            Field modifiersField = Field.class.getDeclaredField("modifiers");
//            modifiersField.setAccessible(true);
            // 设置修饰符：private
//            modifiersField.setInt(actualDataNodesField, actualDataNodesField.getModifiers() & ~Modifier.FINAL);

            // 下面循环次数可以控制当前的项目可以访问的表格（只可以看几天内的数据）
            // 新节点
//            DateTime date = DateUtil.date();
//            int delay = Constant.FIVE;
//            date.offset(DateField.DAY_OF_MONTH, delay);
            List<DataNode> newDataNodes = new ArrayList<>();
//            // 数据源
            String dataSourceName = dataNodes.get(0).getDataSourceName();
//            // 逻辑表名
//            String logicTableName = tableRule.getLogicTable();
//            StringBuilder stringBuilder = new StringBuilder().append(dataSourceName).append(".").append(logicTableName);
//            int length = stringBuilder.length();
//            // 循环动态拼接所有节点表名
//            for (int i = 0; i < Constant.THIRTY+Constant.THIRTY; i++) {
//                stringBuilder.setLength(length);
//                stringBuilder.append(date.toString("_yyyyMMdd"));
//                DataNode dataNode = new DataNode(stringBuilder.toString());
//                newDataNodes.add(dataNode);
//                date = date.offset(DateField.DAY_OF_MONTH, -1);
//            }
            actualDataNodesField.setAccessible(true);
            // 数据更新回去
            actualDataNodesField.set(tableRule, newDataNodes);



            Set<String> actualTables = Sets.newHashSet();
            Map<DataNode, Integer> dataNodeIntegerMap = Maps.newHashMap();

            AtomicInteger a = new AtomicInteger(0);
            newDataNodes.forEach((dataNode -> {
                actualTables.add(dataNode.getTableName());
                if (a.intValue() == 0) {
                    a.incrementAndGet();
                    dataNodeIntegerMap.put(dataNode, 0);
                } else {
                    dataNodeIntegerMap.put(dataNode, a.intValue());
                    a.incrementAndGet();
                }
            }));

            // 动态刷新：actualTables
            Field actualTablesField = TableRule.class.getDeclaredField("actualTables");
            actualTablesField.setAccessible(true);
            actualTablesField.set(tableRule, actualTables);

            // 动态刷新：dataNodeIndexMap
            Field dataNodeIndexMapField = TableRule.class.getDeclaredField("dataNodeIndexMap");
            dataNodeIndexMapField.setAccessible(true);
            dataNodeIndexMapField.set(tableRule, dataNodeIntegerMap);

            // 动态刷新：datasourceToTablesMap
            Map<String, Collection<String>> datasourceToTablesMap = Maps.newHashMap();
            datasourceToTablesMap.put(dataSourceName, actualTables);
            Field datasourceToTablesMapField = TableRule.class.getDeclaredField("datasourceToTablesMap");
            datasourceToTablesMapField.setAccessible(true);
            datasourceToTablesMapField.set(tableRule, datasourceToTablesMap);
        }



    }
}
