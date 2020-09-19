package com.example.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        String res = buf.toString(CharsetUtil.UTF_8);
        System.out.println(ctx.channel().remoteAddress()+"发来了消息："+res);
        String returnMsg = "服务器已收到，谢谢";
        ByteBuf byteBuf = Unpooled.copiedBuffer(returnMsg.getBytes());
        //给客户端回复消息
        ctx.writeAndFlush(byteBuf);

        //在读取消息时可以使用异步的方式来读取：有三种实现方式：
        //1.使用eventLoop.execute()方法实现异步执行
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                ByteBuf buf = (ByteBuf) msg;
                String res = buf.toString(CharsetUtil.UTF_8);
                System.out.println(ctx.channel().remoteAddress()+"发来了消息："+res);
                String returnMsg = "服务器已收到，谢谢";
                ByteBuf byteBuf = Unpooled.copiedBuffer(returnMsg.getBytes());
                //给客户端回复消息
                ctx.writeAndFlush(byteBuf);
            }
        });

        //2.用户自定义定时任务
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                ByteBuf buf = (ByteBuf) msg;
                String res = buf.toString(CharsetUtil.UTF_8);
                System.out.println(ctx.channel().remoteAddress()+"发来了消息："+res);
                String returnMsg = "服务器已收到，谢谢";
                ByteBuf byteBuf = Unpooled.copiedBuffer(returnMsg.getBytes());
                //给客户端回复消息
                ctx.writeAndFlush(byteBuf);
            }
        },5, TimeUnit.SECONDS);//表示  延迟 5  秒  执行
     }


//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        System.out.println("客户端发来了Long类型的数据："+msg);
//    }

    /**
     * 数据读取完毕后触发
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("数据读取完毕！");
    }

    /**
     * 连接成功后自动执行该方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接过来了！");
        ctx.writeAndFlush(Unpooled.copiedBuffer("你好，我是服务器！", Charset.forName("utf-8")));
    }

    /**
     * 失去连接时触发
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("失去连接触发！");
        super.channelInactive(ctx);
    }

    /**
     * 由pipeline链中的IdleStateHandler触发
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("触发了心跳检测："+evt.getClass());
        if (evt instanceof IdleStateEvent)
        {
            IdleStateEvent event = (IdleStateEvent) evt;
            String eventMsg = null;
            switch (event.state())
            {
                case READER_IDLE:
                    eventMsg = "读空闲";
                    break;
                case WRITER_IDLE:
                    eventMsg = "写空闲";
                    break;
                case ALL_IDLE:
                    eventMsg = "读写空闲";
                    break;
            }
//            System.out.println(ctx.channel().remoteAddress()+"通道发生了"+eventMsg+"事件   ");
            //一般在检测到读/写空闲以后会关闭Channel
//            System.out.println("服务器做相应处理！");
        }
    }

    /**
     * 出异常后会触发该方法
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        System.out.println("出现异常");
        //关闭通道
        ctx.close();
    }
}
