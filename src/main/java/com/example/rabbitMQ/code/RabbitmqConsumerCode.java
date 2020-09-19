package com.example.rabbitMQ.code;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * RabbitMQ消费者-不使用注解
 * @Author: HYX
 * @Date: 2020/7/28 19:47
 */
public class RabbitmqConsumerCode {

    public static void main(String[] args) throws IOException, TimeoutException {
        consumerLinster();
//            linsterDelayQueue();
//        linsterDelayQueueByPlugin();
    }

    /**
     * 延迟消费
     * 使用插件实现
     */
    public static void linsterDelayQueueByPlugin()
    {
        Connection connection = null;
        Channel channel = null;
        try {
            connection = RabbitProducerCode.getConnection();
            channel = connection.createChannel();

            //创建被监听队列
//            channel.queueDeclare(RabbitProducerCode.QUEUE,false,false,false,null);

            //监听消息
            DefaultConsumer consumer = new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("路由key为："+envelope.getRoutingKey());
                    System.out.println("交换器为："+envelope.getExchange());
                    System.out.println("消息id为"+envelope.getDeliveryTag());
                    System.out.println("接收到消息："+ new String(body,"utf-8"));
                }
            };
            channel.basicConsume(RabbitProducerCode.QUEUE,true,consumer);
        }catch (Exception e)
        {
            try {
                channel.close();
                connection.close();
            } catch (TimeoutException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 延迟消费
     * 监听死信队列消息
     */
    public static void linsterDelayQueue()
    {
        Connection connection = null;
        Channel channel = null;
        try {
            connection = RabbitProducerCode.getConnection();
            channel = connection.createChannel();

            //创建队列
            Map<String,Object> args = new HashMap<>();
            // x-dead-letter-exchange    这里声明当前队列绑定的死信交换机
            args.put("x-dead-letter-exchange", RabbitProducerCode.DELAY_EXCHANGE);
            // x-dead-letter-routing-key  这里声明当前队列的死信路由key
            args.put("x-dead-letter-routing-key", RabbitProducerCode.DELAY_QUEUE);
            // x-message-ttl  声明队列的TTL
//            args.put("x-message-ttl", 2*60*1000);
            channel.queueDeclare(RabbitProducerCode.QUEUE,false,false,false,args);

            //监听消息
            DefaultConsumer consumer = new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("路由key为："+envelope.getRoutingKey());
                    System.out.println("交换器为："+envelope.getExchange());
                    System.out.println("消息id为"+envelope.getDeliveryTag());
                    System.out.println("接收到消息："+ new String(body,"utf-8"));
                }
            };
            channel.basicConsume(RabbitProducerCode.DELAY_QUEUE,true,consumer);
        }catch (Exception e)
        {
            try {
                channel.close();
                connection.close();
            } catch (TimeoutException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * rabbitmq消息监听者
     * @throws IOException
     * @throws TimeoutException
     */
    public static void consumerLinster() throws IOException, TimeoutException {
        //创建连接
        Connection connection = RabbitProducerCode.getConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //创建队列，并设置消息处理
//        channel.queueDeclare(RabbitProducerCode.QUEUE_NAME,true,false,false,null);
//        AMQP.Queue.DeclareOk declareOk = channel.queueDeclarePassive("vehicle_basic_data_queue");
//        System.out.println("队列信息："+declareOk);
//        Map<String, Object> map = new HashMap<>(1);
//        map.put("x-message-ttl", 60*1000);//设置过期时间为1天
//        channel.queueDeclare(RabbitProducerCode.QUEUE_NAME,true,false,false,map);
        //监听消息
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            /**
             *  用于处理消息的回调函数
             * @param consumerTag 消费者标签 在channel.basicConsumer时候可以指定
             * @param envelope 消息包的内容，从中可以获取消息id，消息routingkey,交换机，消息和重传标志（收到消息失败后是否需要重新发送）
             * @param properties 属性信息
             * @param body 消息
             * @throws IOException
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("路由key为："+envelope.getRoutingKey());
                System.out.println("交换器为："+envelope.getExchange());
                System.out.println("消息id为"+envelope.getDeliveryTag());
                System.out.println("接收到消息："+ new String(body,"utf-8"));
//                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        //监听消息
        //参数一：队列名称，参数二：是否自动确认，true表示收到消息后自动确认，MQ接收到回复后会删除消息
        //参数三：消息接收到后回调
        channel.basicConsume("ttlQueueTest",true,defaultConsumer);
        //不关闭资源，因为需要一直监听消息
        //channel.close;
        //connection.close();
    }
}
