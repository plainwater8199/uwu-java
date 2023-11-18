package com.uwu.study.db.shardingspheredemo.reflsh;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class RefreshActualNodesForShardingJDBC implements ApplicationRunner {


    @Resource
    private ShardingJdbcNodesAutoConfigurator shardingJdbcNodesAutoConfigurator;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<String> customerActualNodes = new ArrayList<>();

        customerActualNodes.add("m0.customer_2795797879");
        customerActualNodes.add("m0.customer_4074863200");
        customerActualNodes.add("m0.customer_5269356437");
        customerActualNodes.add("m0.customer_6595915436");
        customerActualNodes.add("m0.customer_7128266136");
        customerActualNodes.add("m0.customer_8574870324");

        shardingJdbcNodesAutoConfigurator.generateActualDataNodes("customer",customerActualNodes);

    }
}
