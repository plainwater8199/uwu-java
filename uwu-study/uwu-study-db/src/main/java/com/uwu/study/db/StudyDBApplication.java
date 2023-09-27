package com.uwu.study.db;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.uwu.study.db.shardingspheredemo.mapper")
@SpringBootApplication
public class StudyDBApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudyDBApplication.class, args);
    }
}
