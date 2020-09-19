package com.example.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 客户端编码器-演示传输Long类型数据
 * MessageToByteEncoder也是一个泛型类，泛型参数I表示将需要编码的对象的类型，编码的结果是将信息转换成二进制流放入ByteBuf中。
 * 子类通过覆写其抽象方法encode来实现编码
 * @Author: HYX
 * @Date: 2020/7/16 19:46
 */
public class ClientEncode extends MessageToByteEncoder<Integer> {

//    @Override
//    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
//         out.writeLong(msg);
//    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Integer msg, ByteBuf out) throws Exception {
        out.writeLong(msg);
    }
}
