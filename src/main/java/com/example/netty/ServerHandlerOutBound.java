package com.example.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;

/**
 * Netty服务端出战处理器
 * @Author: HYX
 * @Date: 2020/7/16 19:29
 */
public class ServerHandlerOutBound extends ChannelOutboundHandlerAdapter {

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务端出站read方法执行了！");
        super.read(ctx);
    }
}
