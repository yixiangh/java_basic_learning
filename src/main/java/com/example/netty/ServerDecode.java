package com.example.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 服务端解码器-演示传输Long类型
 * @Author: HYX
 * @Date: 2020/7/16 19:46
 */
public class ServerDecode extends ByteToMessageDecoder {
    /**
     * 解码
     * 只有在可读字节数>=4的情况下，我们才进行解码，即读取一个int，并添加到List中。
     * 在可读字节数小于4的情况下，我们并没有做任何处理，假设剩余可读字节数为3，不足以构成1个int。
     * 那么父类ByteToMessageDecoder发现这次解码List中的元素没有变化，
     * 则会对in中的剩余3个字节进行缓存，等待下1个字节的到来，之后再回到调用ToIntegerDecoder的decode方法。
     *
     * ByteToMessageDecoder是将二进制流进行解码后，得到有效报文。而MessageToMessageDecoder则是将一个本身就包含完整报文信息的对象转换成另一个Java对象。
     * @param ctx ChannelHandler上下文
     * @param in 数据缓冲区
     * @param out Handler处理器链
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //java中int类型为4个字节，所以我们判断4个字节读取一次
        if (in.readableBytes() == 4)
        {
            out.add(in.readLong());//将我们读取到的内容交给下一个Handler处理器处理；
        }
    }
}
