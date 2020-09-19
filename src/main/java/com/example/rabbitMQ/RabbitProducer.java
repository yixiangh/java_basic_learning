package com.example.rabbitMQ;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * RabbitMQ生产者
 * @Author: HYX
 * @Date: 2020/7/28 17:25
 */
@Component
public class RabbitProducer {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public String send(String msg)
    {
        System.out.println("发送消息："+msg);
        //参数一：交换器名称，可以省略（省略的话存储到AMQP default交换器）；参数二：路由键名称（direct模式下路由键=队列名称）；参数三：存储消息
        this.rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY,msg);
        return "发送消息成功！";
    }

    public String sendTwo(String msg)
    {
        System.out.println("发送消息："+msg);
        //参数一：交换器名称，可以省略（省略的话存储到AMQP default交换器）；参数二：路由键名称（direct模式下路由键=队列名称）；参数三：存储消息
        this.rabbitTemplate.convertAndSend("sendTwo",msg);
        return "发送消息成功！";
    }

    /**
     * 延迟消息
     * @param msg 消息
     * @param routingKey 路由键
     * @param delay 延迟时间 单位：秒
     * @return
     */
    public String sendDelayMsg(String msg,String routingKey,int delay)
    {
        System.out.println("发送消息："+msg);
        try {
            //参数一：交换器名称，可以省略（省略的话存储到AMQP default交换器）；参数二：路由键名称（direct模式下路由键=队列名称）；参数三：存储消息
            this.rabbitTemplate.convertAndSend(RabbitConfig.DELAY_EXCHANGE,routingKey,msg,message -> {
                message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                message.getMessageProperties().setDelay(delay);
                return message;
            });
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return "发送消息成功！";
    }
}
