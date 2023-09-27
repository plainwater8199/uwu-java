//package com.uwu.study.db.shardingsphere;
//
//import org.apache.shardingsphere.shardingjdbc.jdbc.adapter.AbstractDataSourceAdapter;
//import org.springframework.boot.SpringBootConfiguration;
//import org.springframework.boot.autoconfigure.AutoConfigureAfter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Lazy;
//
//import javax.annotation.Resource;
//
//@Configuration
//@AutoConfigureAfter({DataSourceConfig.class, DynamicDataSourceAutoConfiguration.class, SpringBootConfiguration.class})
//public class ShardingConfigure {
//    @Lazy
//    @Resource(name = "shardingDataSource")
//    private AbstractDataSourceAdapter shardingDataSource;
//
//    @Bean
//    public AutoConfigDataNodes createAutoConfigDataNodes() throws Exception {
//        AutoConfigDataNodes autoConfigDataNodes = new AutoConfigDataNodes(shardingDataSource);
//        return autoConfigDataNodes;
//    }
//}
