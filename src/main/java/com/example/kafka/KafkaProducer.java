package com.example.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
//            ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(TOPIC, msg);
//            SendResult<String, Object> sendResult = future.get();
//            System.out.println("sendResult:"+sendResult);
//            kafkaTemplate.send(TOPIC,msg,new Callback)
        }catch (Exception e)
        {
            System.out.println("Kafka生产消息失败，错误信息："+e.getMessage());
            return "fial";
        }
        return "success";
    }


}
