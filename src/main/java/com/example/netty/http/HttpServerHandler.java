package com.example.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * 服务端自定义处理器
 * @Author: HYX
 * @Date: 2020/7/11 15:28
 * 1.SimpleChannelInboundHandler 是 ChannelInboundHandlerAdapter 的一个子类
 * 2.HttpObject 表示客户端和服务端相互通信的数据都被封装成HttpObject
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpRequest)
        {
            System.out.println("msg类型："+msg.getClass());
            System.out.println("客户端地址"+ctx.channel().remoteAddress());
            //获取客户端的请求 request
            HttpRequest request = (HttpRequest) msg;
            //获取请求的URI
            URI uri = new URI(request.uri());
            if ("/favicon.ico".equals(uri.getPath()))
            {
                System.out.println("客户端在请求图标数据，服务器不做处理");
                return;
            }
            //回复消息给浏览器[http协议]
            ByteBuf content = Unpooled.copiedBuffer("Hello ,我是服务器", CharsetUtil.UTF_8);
            //构造一个Http的响应，即HttpResponse
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());
            //将构建好的response返回即可
            ctx.writeAndFlush(response);
        }
    }
}
