package com.example.rabbitMQ;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * RabbitMQ消费者
 * @Author: HYX
 * @Date: 2020/7/28 17:29
 */
@Component
public class RabbitmqConsumer {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    /**
     * 监听者消费消息
     * @param msg
     */
//    @RabbitHandler
//    @RabbitListener(queues = "vehicle_basic_data_queue")
//    public void process(String msg)
//    {
//        System.out.println("Direct消费到消息："+msg);
//    }

    @RabbitListener(queues = RabbitConfig.DELAY_QUEUE)
    public void delayProcess(String msg)
    {
        System.out.println("消费到延迟消息："+msg);
    }
}
