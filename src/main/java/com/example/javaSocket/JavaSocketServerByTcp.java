package com.example.javaSocket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * socket通信模型之TCP协议
 * Socket服务端
 */
public class JavaSocketServerByTcp {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(6666);//创建socket服务端，并设置端口号
            Socket socket = serverSocket.accept();//accept()为接受来自客户端的请求连接信息，此方法为阻塞式方法，即没有客户端连接的情况下，会一直阻塞在此处，不会往下执行
            System.out.println("连接成功");
            //接收客户端的消息
            InputStream inputStream = socket.getInputStream();//接收客户端消息，即为写入，因此使用InputStream()来接收Socket客户端发来的消息
            DataInputStream dis = new DataInputStream(inputStream);//为了方便我们直接读取字符或数字类型，使用数据流DataInputStream()对输入流InputStream()进行封装
            String result = dis.readUTF();
            System.out.println("收到来自：" + socket.getInetAddress() + ":" + socket.getPort() + "的消息：" + result);
            //向客户端发出消息
            OutputStream outputStream = socket.getOutputStream();//向Socket客户端发出消息
            OutputStreamWriter osw = new OutputStreamWriter(outputStream);
            osw.write("Hello Client!");

            osw.flush();
            osw.close();
            dis.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
