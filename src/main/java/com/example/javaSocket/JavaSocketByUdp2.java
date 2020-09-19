package com.example.javaSocket;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

/**
 * socket通信模型之UDP协议
 * socket：两个java应用程序可通过一个双向的网络通信连接实现数据交互，这个双向链路的一端称为一个socket
 * java.net包中定义的两个类Socket和ServerSocket，分别用来实现双向连接的client和server端
 */
public class JavaSocketByUdp2 {

    /**
     * Socket使用UDP协议传输字符类型
     * 发送端
     */
    public static void socketConnectByUdp2() {
        String message = "Hello UDP1,我是 UDP2!";
        byte buf[] = message.getBytes();
        DatagramPacket dp = new DatagramPacket(buf, buf.length, new InetSocketAddress("127.0.0.1", 5678));//数据包中放入字节数组，数据长度范围，接收方IP+port
        try {
            DatagramSocket ds = new DatagramSocket(8765);
            ds.send(dp);
            ds.close();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Socket使用UDP协议传输double/int/float/long等数字类型
     * 发送端
     */
    public static void socketDoubleByUdp2() {
        long message = 10000L;    //定义要传送的“消息”
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();//在内存中创建一块字节数组区域，用以存放字节数组，并将其转换为字节数组，ByteArrayOutputStream()主要作用为将数据转为字节数组
            DataOutputStream dos = new DataOutputStream(baos);//使用数据流DataOutputStream来写入double/int/float/long等数字类型到字节数组
            dos.writeLong(message);   //将数据写入到字节数组中，以便于转换为字节数组
            dos.flush();
            dos.close();

            byte buf[] = baos.toByteArray();//将刚刚在内存中创建并存放数据的字节数组中的字节数组获取出来
            DatagramPacket dp = new DatagramPacket(buf, 0, buf.length, new InetSocketAddress("127.0.0.1", 6789));//UDP协议发出或接收数据需要使用数据包来对数据做一个包裹，因此创建数据包用于包裹字节数组，需注明使用的数组、数组中数据长度
            DatagramSocket ds = new DatagramSocket(9876);//创建UDP协议的Socket对象用以传输数据,并设置自己端口号
            ds.send(dp);//send()发送消息
            ds.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        socketDoubleByUdp2();
    }
}
