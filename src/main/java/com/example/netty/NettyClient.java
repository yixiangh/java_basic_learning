package com.example.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class NettyClient {

    public static void main(String[] args) {
        nettyCli();
    }

    public static void nettyCli()
    {
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new ClientHandler());
//                        pipeline.addLast(new ClientEncode());
                    }
                });
        bootstrap.connect("127.0.0.1",8989).addListener(future -> {
           if (future.isSuccess())
           {
               System.out.println("连接成功！");
           }else
           {
               System.out.println("连接失败");
               //重连
               connect(bootstrap,"127.0.0.1",8989,5);
           }
        });
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host,port).addListener(future -> {
            if (future.isSuccess())
            {
                System.out.println("重连成功！");
            }else if (retry == 0)
            {
                System.out.println("重连次数已达限制！");
            }else
            {
                // 第几次重连
                int order = (5 - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }


}
