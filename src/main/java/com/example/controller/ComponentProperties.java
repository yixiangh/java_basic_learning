package com.example.controller;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 测试@ConfigurationProperties和@EnableConfigurationProperties的使用
 * @ConfigurationProperties注解可以将配置文件中的值赋给java实体对象的属性
 * @EnableConfigurationProperties注解表示开启@ConfigurationProperties的功能
 */
@Component
@ConfigurationProperties(prefix = "local")
public class ComponentProperties {

    private String host;
    private String port;


    @Override
    public String toString() {
        return "ComponentProperties [host=" + host + ", port=" + port + "]";
    }


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
