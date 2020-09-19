package com.example.javaSocket;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * socket通信模型之UDP协议
 * socket：两个java应用程序可通过一个双向的网络通信连接实现数据交互，这个双向链路的一端称为一个socket
 * java.net包中定义的两个类Socket和ServerSocket，分别用来实现双向连接的client和server端
 */
public class JavaSocketByUdp1 {

    /**
     * Socket使用UDP协议传输字符类型
     * 接收端
     */
    public static void socketConnectByUdp1() {
        try {
            DatagramSocket dgs = new DatagramSocket(5678);//创建Socket用于发送/接收数据
            byte buf[] = new byte[1024];                       //定义长度为1024的字节数组
            DatagramPacket dgp = new DatagramPacket(buf, buf.length);//因为UDP发送和接收数据需要通过包来实现，因此在此定义这个包，包里边存放的是具体的数据载体byte数组
            dgs.receive(dgp);                                   //receive用于接收消息，并存放到上一步创建的包dgp中
            String res = new String(buf, 0, dgp.getLength());//将字节数组转换为字符串，从数组的0位开始到dgp包中数据的长度
            System.out.println("接收到消息：" + res);
            dgs.close();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Socket使用UDP协议传输double/int/float/long等数字类型
     * 接收端
     */
    public static void socketDoubleByUdp1() {
        try {
            byte buf[] = new byte[1024];//定义长度为1024的字节数组，用于存放字节信息
            DatagramSocket ds = new DatagramSocket(6789);//创建UDP协议的Socket对象用以传输数据,并设置自己端口号
            DatagramPacket dp = new DatagramPacket(buf, 0, buf.length);//UDP协议发出或接收数据需要使用数据包来对数据做一个包裹，因此创建数据包用于包裹字节数组，需注明使用的数组、数组中数据长度
            ds.receive(dp);//receive()用以接收发送端发出的消息
            //以上操作为，定义数据载体字节数组以及一个DatagramPacket()来包裹字节数组，DatagramSocket()使用DatagramPacket()来接收数据，最终数据会被存放在buf[]中
            //下边对buf[]中的数据进行读取
            ByteArrayInputStream bais = new ByteArrayInputStream(buf);//在内存中创建一块字节数组区域，用以存放字节数组
            DataInputStream dis = new DataInputStream(bais);//使用数据流DataInputStream来读取double/int/float/long等数字类型到字节数组
            long message = dis.readLong();  //读取
            System.out.println("接收到的消息为：" + message);

            dis.close();
            ds.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        socketDoubleByUdp1();
    }
}
