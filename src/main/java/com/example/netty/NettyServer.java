package com.example.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * Bootstrap:是引导的意思，Netty通常是由一个Bootstrap开始，主要作用是配置真个Netty程序，串联各个组件。ServerBootstrap是服务端的引导类，Bootstrap是客户端引导类
 * Netty中的所有IO操作都是异步的,不能立刻得知消息是否被正确处理，一般通过注册一个监听也就是通过Future和ChannelFutures,通过他们可以注册一个监听，擦欧总执行完毕后能够触发，从而可以判断是否正常执行
 * Netty是基于selector对象实现IO多路复用，通过selector一个线程就可以监听多个连接的Channel事件
 * ChannelHandler是一个接口，处理IO事件或拦截IO操作，并将其转发到其ChannelPipeline(业务处理链)中的下一个处理程序
 * ChannelPipeline是一个Handler的集合，负责管理所有的Handler,要给Channel中有一个ChannelPipeline,一个Channelpipeline中维护了由ChannelHandlerContext组成的双向链表，并且每个ChannelHandlerContext中又关联着一个ChannelHandler
 * ChannelOption：在创建Channel实例后一般需要设置ChannelOption参数
 *      ChannelOption.SO_BACKLOG:用来初始化服务器连接队列大小，服务器处理客户端连接请求是顺序处理，所以同一时间只能处理一个客户端连接，多个客户端来的时候，服务器将不能处理的客户端连接请求放在队列中等待处理，backlog参数指定了该队列的大小
 *      ChannelOption.SO_KEEPALIVE:表示一直保持连接状态
 *  Unpooled：是Netty提供的一个专门用来操作缓冲区的（即Netty的数据容器）的工具类
 *      常用的方法有：copiedBuffer
 */
public class NettyServer {


    public static void main(String[] args) {
        try {
            nettServer();
        }catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public static void nettServer() throws InterruptedException {

        //创建两个线程组，用于处理连接请求和处理业务
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        //创建服务器端的启动对象，并配置参数
        ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap.group(bossGroup,workGroup)//将我们创建的两个线程组赋给启动对象
                .channel(NioServerSocketChannel.class)//设置使用的通道类型
                .handler(new LoggingHandler(LogLevel.INFO))//给bossGroup增加一个日志处理器
                .option(ChannelOption.SO_BACKLOG,1024)//设置线程队列等待连接的个数
                .childOption(ChannelOption.SO_KEEPALIVE,true)//设置保持活动连接状态一般如果两个小时内没有数据的通信时,TCP会自动发送一个活动探测数据报文
                .childOption(ChannelOption.TCP_NODELAY, true)//将小的数据包包装成更大的帧进行传送，提高网络的负载,表示是否开启Nagle算法，true表示关闭，false表示开启，通俗地说，如果要求高实时性，有数据发送时就马上发送，就关闭，如果需要减少发送次数减少网络交互，就开启。
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    /**
                     * Pipeline为管道，可理解为对所有Handler的一个管理，里边放的是所有的Handler处理对象
                     * @param ch
                     * @throws Exception
                     */
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        //IdleStateHandler是Netty提供的处理空闲状态的处理器
                        // Long readerIdleTime:表示多长时间没有读，就会发送一个心跳检测包检测是否连接
                        // Long writerIdleTime:表示多长时间没有写，就会发送一个心跳检测包检测是否连接
                        // Long allIdleTime:表示多长时间没有读写，就会发送一个心跳检测包检测是否连接
                        //当IdleStateEvent触发后，就会传递给管道（pipeLine）的下一个handler处理，通过调用下一个handler的userEventTiggered,在该方法中去处理当IdleStateEvent(读空闲，写空闲，读写空闲)
                        pipeline.addLast(new IdleStateHandler(3,5,7, TimeUnit.SECONDS));
//                        pipeline.addLast(new ServerDecode());//解码
                        pipeline.addLast(new ServerHandler());//将我们自己的Handler处理对象放入PipeLine管道


                    }
                });
        System.out.println("服务器准备好了");
        //绑定端口并设置同步，返回ChannelFuture对象
        ChannelFuture future = bootstrap.bind(8989).sync();
        //ChannelFuture是netty提供的Future_Listener机制，作用是采用回调方法异步处理，在绑定端口操作完成后，
        // 会回调我们添加的Listener方法，从而我们可以得知绑定结果
        //添加对绑定端口结果的监听事件，也就是说在bind方法执行完成后，会触发，可以在监听方法里判断是否绑定成功
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess())
                    {
                        System.out.println("端口绑定成功！");
                    }else
                    {
                        System.out.println("监听端口失败！");
                    }
            }
        });
        //监听通道关闭事件
        future.channel().closeFuture().sync();
    }
}
