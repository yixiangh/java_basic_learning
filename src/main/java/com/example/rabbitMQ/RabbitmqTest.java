package com.example.rabbitMQ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @Author: HYX
 * @Date: 2020/7/28 17:48
 */
@RestController
@RequestMapping(value = "rabbitmq")
public class RabbitmqTest {

    @Autowired
    private RabbitProducer rabbitProducer;

    @RequestMapping(value = "send")
    public String rabbitmqSend(String msg)
    {
        return rabbitProducer.send(msg);
    }

    @RequestMapping(value = "sendDelay")
    public String rabbitmqSendDelay(String msg,int time)
    {
        return rabbitProducer.sendDelayMsg(msg,RabbitConfig.DELAY_QUEUE,time);
    }
}
