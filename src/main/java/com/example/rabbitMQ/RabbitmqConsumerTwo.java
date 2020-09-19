package com.example.rabbitMQ;

import com.alibaba.fastjson.JSONObject;
import com.example.entity.VehicleBasicData;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * RabbitMQ消费者二
 * @Author: HYX
 * @Date: 2020/7/28 17:29
 */
@Component
//@RabbitListener(queues = "vehicle_basic_data_queue")
public class RabbitmqConsumerTwo {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    /**
     * 监听者消费消息
     * @param msg
     */
    @RabbitHandler
    public void process(String msg)
    {
        System.out.println("Direct二消费到消息："+ JSONObject.parseObject(msg, VehicleBasicData.class));
    }
}
