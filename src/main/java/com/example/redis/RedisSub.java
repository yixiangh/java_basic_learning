package com.example.redis;

import org.springframework.stereotype.Service;

/**
 * 订阅者
 * @Author: HYX
 * @Date: 2020/7/9 21:04
 */
@Service
public class RedisSub {

    public void receiveMessage(String message,String topic)
    {
        System.out.println("Redis订阅者接收到消息："+topic+"下的："+message);
    }

    public void catMessage(String message,String topic)
    {
        System.out.println("Rediscat订阅者接收到消息："+topic+"下的："+message);
    }


}
