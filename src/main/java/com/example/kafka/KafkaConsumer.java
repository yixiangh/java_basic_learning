package com.example.kafka;

//import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * kafak消费者
 * @Author: HYX
 * @Date: 2020/8/22 10:01
 */
@Component
public class KafkaConsumer {

//    @KafkaListener(topics = KafkaProducer.TOPIC, groupId = "test_group_a")
    public void kafkaListenerA(String message)
    {
        System.out.println("kafka消费者A消费到消息："+message);
    }

//    @KafkaListener(topics = KafkaProducer.TOPIC,groupId = "test_group_b")
    public void kafkaListenerB(String message)
    {
        System.out.println("kafka消费者B消费到消息："+message);
    }
}
