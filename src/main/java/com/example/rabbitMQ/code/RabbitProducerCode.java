package com.example.rabbitMQ.code;


import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * RabbitMQ生产者-不适用注解
 * @Author: HYX
 * @Date: 2020/7/28 19:10
 */
public class RabbitProducerCode {

//    static final String QUEUE_NAME = "simple_queue";
    public static final String QUEUE_NAME = "ttl_queue";

    public static final String QUEUE = "queueTest";//普通队列
    public static final String ROUTING_KEY = "delay.test";//路由键
    public static final String EXCHANGE = "exchange";//普通交换器
    public static final String EXCHANGE_TYPE = "topic";//交换器类型
    public static final String DELAY_QUEUE = "delayQueueTest";      //死信队列
    public static final String DELAY_EXCHANGE = "delayExchangeTest";//死信交换器

    static final String HOST = "192.168.10.129";
    static final Integer PORT=5672;
    static final String VIRTUAL_HOST="/testVirtual";
    static final String USER_NAME="test";
    static final String PASSWORD="test";

    public static void main(String[] args) throws IOException, TimeoutException {
        queue();
//        ttlQueue();
//        delayQueue();
//        delayQueueByPlugin();
    }

    /**
     * 延迟队列-使用插件实现
     */
    public static void delayQueueByPlugin()
    {
        Connection connection = null;
        Channel channel = null;
        try {
            connection = getConnection();
            channel = connection.createChannel();

            // 声明x-delayed-type类型的exchange
            Map<String, Object> exchangeArgs = new HashMap<>();
            exchangeArgs.put("x-delayed-type", "direct");
            channel.exchangeDeclare(EXCHANGE,"x-delayed-message",true,false,exchangeArgs);
            //声明队列
            channel.queueDeclare(QUEUE,false,false,false,null);
            //交换器-队列绑定
            channel.queueBind(QUEUE,EXCHANGE,ROUTING_KEY);

            for (int i = 0; i < 2; i++) {
                //发送消息
                //消息过期时间
                Integer expi;
                if (i == 0)
                {
                    expi = 120*1000;
                }else
                {
                    expi = 60*1000;
                }
                System.out.println("第"+i+"条消息过期时间为："+expi);
                Map<String,Object> hearders = new HashMap<>();
                hearders.put("x-delay", expi);
                AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                        .headers(hearders)//设置消息发送时间
                        .build();
                String message = "第"+i+"条延迟队列消息来了！";
                channel.basicPublish(EXCHANGE,ROUTING_KEY,properties,message.getBytes());
            }



        } catch (Exception e) {
            try {
                connection.close();
                channel.close();
            } catch (TimeoutException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 延迟队列
     * 利用rabbitmq的TTL加死信队列实现
     */
    public static void delayQueue()
    {
        Connection connection = null;
        Channel channel = null;
        try {
            connection = getConnection();
            channel = connection.createChannel();

            //声明死信交换器
            channel.exchangeDeclare(DELAY_EXCHANGE,"direct");
            //声明死信队列
            channel.queueDeclare(DELAY_QUEUE,false,false,false,null);
            //死信交换器-死信队列绑定
            channel.queueBind(DELAY_QUEUE,DELAY_EXCHANGE,DELAY_QUEUE);

            //声明交换器
            channel.exchangeDeclare(EXCHANGE,EXCHANGE_TYPE);
            //声明队列
            //重点：绑定私信交换器
            Map<String,Object> args = new HashMap<>();
            // x-dead-letter-exchange    这里声明当前队列绑定的死信交换机
            args.put("x-dead-letter-exchange", DELAY_EXCHANGE);
            // x-dead-letter-routing-key  这里声明当前队列的死信路由key
            args.put("x-dead-letter-routing-key", DELAY_QUEUE);
            // x-message-ttl  声明队列的TTL
//            args.put("x-message-ttl", 2*60*1000);
            channel.queueDeclare(QUEUE,false,false,false,args);
            //交换器-队列绑定
            channel.queueBind(QUEUE,EXCHANGE,ROUTING_KEY);

            for (int i = 0; i < 2; i++) {
                //发送消息
                //消息过期时间
                Integer expi;
                if (i == 0)
                {
                    expi = 120*1000;
                }else
                {
                    expi = 60*1000;
                }
                System.out.println("第"+i+"条消息过期时间为："+expi);
                AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                        .expiration(expi.toString())//单独设置消息过期时间
                        .build();
                String message = "第"+i+"条延迟队列消息来了！";
                channel.basicPublish(EXCHANGE,ROUTING_KEY,properties,message.getBytes());
            }



        } catch (Exception e) {
            try {
                connection.close();
                channel.close();
            } catch (TimeoutException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 创建一般队列 消息过期时间由消息自己定义
     */
    public static void queue()
    {
        Connection connection = null;
        Channel channel = null;
        String queueName = "ttlQueueTest";
        String exchangeName = "exchangeTest";
        String routingKey = "test.key";
        String exchangeType = "direct";
        try {
            connection = getConnection();
            channel = connection.createChannel();
            //声明（创建）交换器
            // 第一个参数，exchange：交换机名称。数据类型：String
            // 第二个参数，type：交换机的类型(direct/topic/fanout)。数据类型：String
            channel.exchangeDeclare(exchangeName,exchangeType);
            //声明（创建）队列
            //参数一：队列名称，
            // 参数二：是否定义持久化队列，
            // 参数三：是否独占本次连接，指是否一个channel占用一个connection
            // 参数四：是否在不使用的时候自动删除队列，参数五：队列其他参数
            channel.queueDeclare(queueName,false,false,false,null);

            //交换器-队列绑定
            //第一个参数，queueName:对列名称。数据类型：String
            //第二个参数，exchange：交换机名称。数据类型：String
            //第三个参数，routingKey：队列跟交换机绑定的键值。数据类型：String
            channel.queueBind(queueName,exchangeName,routingKey);
            Integer expr = 90*1000;
            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                    .expiration(String.valueOf(expr))//设置过期时间
                    .build();
            channel.basicPublish(exchangeName,routingKey,properties,"每条消息单独设置过期时间".getBytes());
        }catch (Exception e)
        {
            try {
                connection.close();
                channel.close();
            }catch (TimeoutException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    /**
     * 创建有过期时间的队列
     */
    public static void ttlQueue() throws IOException, TimeoutException {
        Connection connection = null;
        Channel channel = null;
        try {
            //创建通道
            connection = getConnection();
            channel = connection.createChannel();
            //声明（创建）队列
            //参数一：队列名称，
            // 参数二：是否定义持久化队列，
            // 参数三：是否独占本次连接，指是否一个channel占用一个connection
            // 参数四：是否在不使用的时候自动删除队列，参数五：队列其他参数
            Map<String, Object> map = new HashMap<>(1);
            //        args.put("x-dead-letter-exchange", "mine-dead-letter-exchange");
            //        args.put("x-dead-letter-routing-key", "mine.dead.letter");

            //过期时间由队列统一设置
            //注意不是 x-expires,x-expires为队列存活时间,
            // x-message-ttl为队列内的消息存活时间
            //注意更改队列/交换机设置需要删除原有的
            map.put("x-message-ttl", 60 * 1000);//设置过期时间为1分钟
            channel.queueDeclare(QUEUE_NAME, true, false, false, map);
            //        //发送消息
            String message = "舒克是个开飞机的，贝塔是个开坦克的";

            for (int i = 0; i < 10; i++) {
                message += i;
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                System.out.println("发送了消息：" + message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //释放资源
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

    public static Connection getConnection() throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //主机地址：默认为localhost
//        connectionFactory.setHost("10.13.4.33");
        connectionFactory.setHost(HOST);
        //连接端口
        connectionFactory.setPort(PORT);
        //虚拟主机名称，默认为/
//        connectionFactory.setVirtualHost("vehicle");
        connectionFactory.setVirtualHost(VIRTUAL_HOST);
        //连接用户名，默认为guest
//        connectionFactory.setUsername("vehicle");
        connectionFactory.setUsername(USER_NAME);
        //连接密码，默认为guest
//        connectionFactory.setPassword("dkfj@dljK");
        connectionFactory.setPassword(PASSWORD);
        //创建连接
        return connectionFactory.newConnection();
    }
}
