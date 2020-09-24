package com.example.javaFile.javaIo.bio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Bio服务端-同步阻塞
 * @Author: HYX
 * @Date: 2020/7/11 10:16
 */
public class BioServer {

    public static void main(String[] args) throws IOException {
        ServerSocket socket = null;
        try {
            socket = new ServerSocket();
            socket.bind(new InetSocketAddress("127.0.0.1",8989));
            while (true)
            {
                Socket accept = socket.accept();    //阻塞在这里，等待客户端连接
                new Thread(() ->{                   //新建一个线程处理数据
                        handel(accept);
                }).start();
            }
        }catch (IOException e)
        {
            e.printStackTrace();
            socket.close();
        }

    }

    public static void handel(Socket accept)
    {

        try {
            byte[] bytes = new byte[1024];
            //由于BIO的读取 写入数据时单向的  所以我们在这里先获取客户端发过来的消息
            int read = accept.getInputStream().read(bytes); //read方法也会阻塞 下边的write方法也会阻塞
            System.out.println("接收到消息："+new String(bytes,0,read));

            accept.getOutputStream().write(bytes,0,read);   //将数据再写会Client 该方法也会阻塞
            accept.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
