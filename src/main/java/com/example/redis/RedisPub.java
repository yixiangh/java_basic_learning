package com.example.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Redis发布者
 * 使用定时任务模拟发布消息
 * @Author: HYX
 * @Date: 2020/7/9 19:28
 */
@EnableScheduling
@Component
public class RedisPub {

    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate stringRedisTemplate;

//    @Scheduled(fixedRate = 5000)
    public void sendMsg()
    {
        System.out.println("Redis发布者发布了消息！");
        stringRedisTemplate.convertAndSend("index","Hello index");
        stringRedisTemplate.convertAndSend("test","Hello test");
    }




}
