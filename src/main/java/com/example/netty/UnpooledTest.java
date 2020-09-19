package com.example.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * Netty之Unpooled类的使用
 * @Author: HYX
 * @Date: 2020/7/11 17:03
 */
public class UnpooledTest {

    public static void main(String[] args) {
//        ByteBufTestMain1();
        ByteBufTestMain2();
    }

    /**
     * ByteBuf通过readerIndex、writerIndex、capacity将buffer分为三个区域
     * 0->readerIndex为已经读取的区域
     * readerIndex->writeIndex为可读区域
     * writeIndex->capacity为可写区域
     */
    public static void ByteBufTestMain1()
    {
        //创建要给ByteBuf 相当于Nio中的ByteBuffer
        ByteBuf buffer = Unpooled.buffer(10);
        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }
        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.getByte(i));
            System.out.println(buffer.readByte());
        }
    }

    public static void ByteBufTestMain2()
    {
        ByteBuf buf = Unpooled.copiedBuffer("hello,world!", CharsetUtil.UTF_8);
        if (buf.hasArray())//判断Buf是否为空
        {
            //获取buf的大小
            System.out.println("buf大小："+buf.capacity());
            //获取buf中可读取字节数量
            System.out.println("buf可读字节数："+buf.readableBytes());
            //读取一个字节
            System.out.println("读取了一个字节："+buf.readByte());
            //再次获取buf中可读字节数
            System.out.println("buf可读字节数："+buf.readableBytes());
            byte[] array = buf.array();//将buf转为字节数组
            //重新将字节数组转为字符串
            String res = new String(array,CharsetUtil.UTF_8);
            System.out.println(res);
            for (int i = 0; i < buf.readableBytes(); i++) {
                System.out.println((char)buf.getByte(i));//默认读取的是Ascii码  强制转为char字符
            }
            //读取其中的一部分,类似与字符串截取，就是说读取一个范围内的字节
            System.out.println(buf.getCharSequence(0,4, Charset.forName("UTF-8")));
        }


    }


}
