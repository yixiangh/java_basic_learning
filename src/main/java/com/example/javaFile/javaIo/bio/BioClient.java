package com.example.javaFile.javaIo.bio;

import java.io.IOException;
import java.net.Socket;

/**
 * Bio客户端 -同步阻塞
 * @Author: HYX
 * @Date: 2020/7/11 10:23
 */
public class BioClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1",8989);
        socket.getOutputStream().write("Hello server!".getBytes());
        socket.getOutputStream().flush();

        System.out.println("等待服务器将消息返回回来。。。。。。");
        byte[] bytes = new byte[1024];//定义字节数组，将消息放入其中
        int read = socket.getInputStream().read(bytes);//读取数据到bytes数组中，返回数据长度
        System.out.println(new String(bytes,0,read));//将bytes数组中的数据转为String输出出来 从0开始到数据长度也就是最后
        socket.close();

    }


}
