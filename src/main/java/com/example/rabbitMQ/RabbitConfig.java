package com.example.rabbitMQ;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 交换器配置
 * @Author: HYX
 * @Date: 2020/7/28 17:31
 */
@Configuration
public class RabbitConfig {
    public final static String QUEUE_NAME = "testQueue";      //队列名称
    public final static String EXCHANGE_NAME = "topicTestExchange"; //交换器名称
    public final static String ROUTING_KEY = "test.hyx";//路由键

    public final static String DELAY_QUEUE = "delayQueue";
    public final static String DELAY_EXCHANGE = "delayExchange";
    public final static String DELAY_ROUTING_KEY = "delay.test.hyx";

    /**
     * 声明队列：参数一：队列名称，参数二：是否持久化
     * @return
     */
    @Bean("myQueue")
    public Queue queue()
    {
        return new Queue(RabbitConfig.QUEUE_NAME,false);
    }

    /**
     * 声明延迟队列
     * @return
     */
    @Bean("delayQueue")
    public Queue delayQueue()
    {
        return new Queue(DELAY_QUEUE,false);
    }

    /**
     * 声明延迟交换器
     * @return
     */
    @Bean("delayExchange")
    public Exchange delayExchange()
    {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        DirectExchange exchange = new DirectExchange(DELAY_EXCHANGE,false,true,args);
        exchange.setDelayed(true);
        return exchange;
    }

    /**
     * 声明交换器
     * 配置默认交换器，以下部分可以不配置，不设置使用默认交换器（AMQP default）
     * 参数一：交换器名称，参数二：是否持久化，参数三：是否自动删除消息
     * @return
     */
    @Bean("topicExchange")
    Exchange exchange()
    {
        return new TopicExchange(RabbitConfig.EXCHANGE_NAME,false,true);//(DirectConfig.EXCHANGE_NAME,false,false);
    }

    /**
     * 将队列绑定到交换器
     * @return
     */
    @Bean
    Binding bindingExchangeDirectQueue(@Qualifier("myQueue") Queue queue,
                                       @Qualifier("topicExchange") Exchange exchange)
    {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY).noargs();
    }

    /**
     * 将队列绑定到交换器
     * @return
     */
    @Bean
    Binding bindingExchangeQueueByDelay(@Qualifier("delayQueue") Queue queue,
                                       @Qualifier("delayExchange") Exchange exchange)
    {
        return BindingBuilder.bind(queue).to(exchange).with(DELAY_QUEUE).noargs();
    }


}
