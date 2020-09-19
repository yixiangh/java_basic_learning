package com.example.javaSocket;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * socket通信模型之TCP协议
 * socket：两个java应用程序可通过一个双向的网络通信连接实现数据交互，这个双向链路的一端称为一个socket
 * java.net包中定义的两个类Socket和ServerSocket，分别用来实现双向连接的client和server端
 */
public class JavaSocketClientByTcp {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 6666);//创建socket客户端，并设置监听地址及端口
            //向服务端发出消息
            OutputStream outputStream = socket.getOutputStream();//因为客户端是要向服务端发出消息，即写出，因此使用OutputStream()来向socket服务端发送消息
            DataOutputStream dos = new DataOutputStream(outputStream);//为了方便我们直接写入字符或数字类型，使用数据流DataOutputStream()对输出流OutputStream()进行封装
            dos.writeUTF("Hello Server!");//向输出流中写入UTF编码的字符串
            //接收服务端的消息
            InputStream inputStream = socket.getInputStream();  //接收服务端发出的消息对于客户端来说为写入，因此使用InputStream()来接收socket服务端发送的消息
            InputStreamReader isr = new InputStreamReader(inputStream);//转换为字符流
            BufferedReader br = new BufferedReader(isr);//转换为缓冲流，从而使用缓冲流的readLine()方法直接读取一行
            System.out.println(br.readLine());

            dos.flush();//将输出流管道中的数据做一次推送，保证完全发出去
            dos.close();//关闭输出流
            isr.close();//关闭输入流
            socket.close();//关闭socket
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
