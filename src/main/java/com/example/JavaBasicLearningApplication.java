package com.example;

import com.example.controller.ComponentProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(value = {ComponentProperties.class})
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication
@MapperScan(basePackages = {"com.example.dao"})
public class JavaBasicLearningApplication {

    public static void main(String[] args) {
       SpringApplication.run(JavaBasicLearningApplication.class, args);
    }

}
