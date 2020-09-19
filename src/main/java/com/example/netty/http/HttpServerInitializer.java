package com.example.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 将
 * @Author: HYX
 * @Date: 2020/7/11 15:29
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //加入Netty提供的编解码器-httpServerCodec
        pipeline.addLast("MyHttpServerCodec",new HttpServerCodec());
        pipeline.addLast(new HttpServerHandler());

    }
}
