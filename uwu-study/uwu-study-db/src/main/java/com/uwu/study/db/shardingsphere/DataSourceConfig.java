//package com.uwu.study.db.shardingsphere;
//
//import org.apache.shardingsphere.shardingjdbc.jdbc.adapter.AbstractDataSourceAdapter;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.SpringBootConfiguration;
//import org.springframework.boot.autoconfigure.AutoConfigureBefore;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.*;
//
//import javax.annotation.Resource;
//
//@Configuration
//@ComponentScan(basePackages = {"org.apache.shardingsphere.shardingjdbc"})
////@AutoConfigureBefore({DynamicDataSourceAutoConfiguration.class, SpringBootConfiguration.class})
//@ConditionalOnProperty(prefix = "spring.shardingsphere",
//        name = {"enabled"},
//        havingValue = "true",
//        matchIfMissing = true)
//public class DataSourceConfig {
//    @Value("${spring.shardingsphere.enabled:true}")
//    private boolean shardingEnabled;
//
//    @Value("${spring.datasource.dynamic.primary:}")
//    private String primaryDataSource;
//
////    @Resource
////    private DynamicDataSourceProperties properties;
////
////    @Lazy
////    @Resource(name = "shardingDataSource")
////    private AbstractDataSourceAdapter shardingDataSource;
////
////    @Bean
////    public DynamicDataSourceProvider dynamicDataSourceProvider() {
////        Map<String, DataSourceProperty> datasourceMap = properties.getDatasource();
////        return new AbstractDataSourceProvider() {
////            @Override
////            public Map<String, DataSource> loadDataSources() {
////                Map<String, DataSource> dataSourceMap = createDataSourceMap(datasourceMap);
////                if (shardingEnabled) {
////                    dataSourceMap.put("sharding-data-source", shardingDataSource);
////                }
////                return dataSourceMap;
////            }
////        };
////    }
////
////    @Primary
////    @Bean
////    public DataSource dataSource(DynamicDataSourceProvider dynamicDataSourceProvider) {
////        DynamicRoutingDataSource dataSource = new DynamicRoutingDataSource();
////        dataSource.setPrimary(properties.getPrimary());
////        dataSource.setStrict(properties.getStrict());
////        dataSource.setStrategy(properties.getStrategy());
////        dataSource.setP6spy(properties.getP6spy());
////        dataSource.setSeata(properties.getSeata());
////        if (StringUtils.isBlank(primaryDataSource) && shardingEnabled) {
////            dataSource.setPrimary("sharding-data-source");
////        }
////        return dataSource;
////    }
//}
