package com.example.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;


public class ClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 通道就绪事件
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive方法执行写数据");
//        ByteBuf buffer = ctx.alloc().buffer();
//        String msg = "你好，我是客户端！";
//        byte[] bytes = msg.getBytes(CharsetUtil.UTF_8);
//        buffer.writeBytes(bytes);
//        ctx.writeAndFlush(buffer);
//        ctx.writeAndFlush(Unpooled.copiedBuffer("你好，我是客户端！",CharsetUtil.UTF_8));
        ctx.writeAndFlush(ctx.alloc().buffer().writeInt(123456));
    }

    /**
     * 通道读取事件发生
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        String res = msg.toString();
//        System.out.println("客户端收到数据+"+res);
        ByteBuf buf= (ByteBuf) msg;
        System.out.println("客户端收到数据+"+buf.toString(CharsetUtil.UTF_8));
    }

    /**
     * 通道注册事件发生
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("通道注册事件触发了");
        super.channelRegistered(ctx);
    }

    /**
     * 如果当前handler被添加到pipeline时触发
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handler被添加到pipeline了");
        super.handlerAdded(ctx);
    }

    /**
     * 如果当前handler被移除当前pipeline时触发
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handler被移除pipeline了");
        super.handlerRemoved(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive方法执行了");
        super.channelInactive(ctx);
    }
}
