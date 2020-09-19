package com.example.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * kafka消息生产者
 * @Author: HYX
 * @Date: 2020/8/22 10:01
 */
@RestController
@RequestMapping(value = "kafka")
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    public static final String TOPIC="testTopic";

    @RequestMapping(value = "sendMsg")
    public String send(String msg)
    {
        try {
            kafkaTemplate.send(TOPIC,msg);
        }catch (Exception e)
        {
            System.out.println("Kafka生产消息失败，错误信息："+e.getMessage());
            return "fial";
        }
        return "success";
    }


}
