package com.example.javaIo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * nio通信-客户端
 * socketChannel相当于Socket中的Socket,表示开启一个客户端Socket
 */
public class NioClient {
    public static void main(String[] args) throws IOException {
        socketChannelClient();
    }

    public static void socketChannelClient() throws IOException {
        //创建一个socket客户端
        SocketChannel socketChannel = SocketChannel.open();
        //设置为非阻塞模式
        socketChannel.configureBlocking(false);
        //设置服务器地址
        InetSocketAddress addr = new InetSocketAddress("127.0.0.1",8899);
        //连接服务器 socketChannel.connect(addr)返回的是 是否来连接上了服务器
        if (!socketChannel.connect(addr))//没连上
        {
            System.out.println("未连接成功");
//            while (socketChannel.finishConnect())
////            {
////                System.out.println("在没连接上时，客户端可以做其他事情，不会阻塞");
////            }
        }
        boolean bool = socketChannel.finishConnect();
        System.out.println("连接上服务端了，开始发送信息！"+bool);
        //如果连接上了
        String msg = "Hello 服务端,你好啊";
        //使用wrap()可直接讲字节数组放入Buffer中
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
        //讲buffer数据放入channel通道中发送出去
        socketChannel.write(buffer);
        //使用 read() 将程序停止在此处
        System.in.read();

    }



}
