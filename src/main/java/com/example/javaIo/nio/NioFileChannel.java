package com.example.javaIo.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioFileChannel {

    public static void main(String[] args) throws IOException {
//        writeFile();//写入文件
//        readFile();//读取文件
        copyFileByBuffer();//使用Buffer缓冲区复制文件
        copyImgByChannel();//使用Channel通道复制图片
    }

    /**
     * 利用Channel和Buffer将字符串写入到文件中
     */
    public static void writeFile() throws IOException {
        String str = "hello,黄伊祥";
        //创建一个输出流
        FileOutputStream fileOutputStream = new FileOutputStream("E:\\hyx_hello.txt");
        //根据输出流获取对应的Channel通道
        FileChannel fileChannel = fileOutputStream.getChannel();
        //创建一个Buffer缓冲区 定义长度为1024个字节
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //将字符串str放入缓冲区
        buffer.put(str.getBytes());
        //对buffer进行读写转换,也就是将之前的写缓存  转换为  读缓存
        buffer.flip();
        //将缓冲区的内容写入Channel通道
        fileChannel.write(buffer);
        //关闭输出流
        fileOutputStream.close();
    }

    /**
     * 将文件中的内容通过Channel和Buffer读取出来
     */
    public static void readFile() throws IOException {
        //读取文件
        File file = new File("E:\\hyx_hello.txt");
        //创建输入流
        FileInputStream fileInputStream = new FileInputStream(file);
        //获取输入流通道
        FileChannel channel = fileInputStream.getChannel();
        //创建长度为文件长度的Buffer缓冲区
        ByteBuffer buffer = ByteBuffer.allocate((int) file.length());
        //将Channel通道中的数据读取到Buffer缓冲区
        channel.read(buffer);
        //读取缓冲区的内容并转为String类型
        System.out.println(new String(buffer.array()));
        //关闭输入流
        fileInputStream.close();
    }

    /**
     * 使用FileChannel(通道)和方法read()/write(),实现文件的复制
     * 使用一个Buffer
     */
    public static void copyFileByBuffer() throws IOException {
        File file = new File("E:\\hyx_hello.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel inputChannel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("E:\\hyx_hello02.txt");
        FileChannel outputChannel = fileOutputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(512);

        while (true)
        {
            buffer.clear();//清除缓冲区内容，以便于下次存入（主要就是将buffer中的各个属性值重置，比如position/limit等初始化一下）
            int bool = inputChannel.read(buffer);//将输入通道的数据读取到buffer缓冲区
            if (bool == -1) //判断是否还有数据
            {
                break;
            }
            buffer.flip();  //对buffer进行读写转换
            outputChannel.write(buffer);    //将buffer中的数据写入输出通道中

        }
        fileInputStream.close();
        fileOutputStream.close();
    }

    /**
     * 使用Channel通道实现复制图片
     */
    public static void copyImgByChannel() throws IOException
    {
        File file = new File("E:\\01.jpg");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel inputChannel = fileInputStream.getChannel();
        FileOutputStream fileOutputStream = new FileOutputStream("E:\\02.jpg");
        FileChannel outputChannel = fileOutputStream.getChannel();
        //使用transferFrom将输入通道复制给输出通道
        long aa = outputChannel.transferFrom(inputChannel,0,inputChannel.size());
        System.out.println(aa);
        inputChannel.close();
        outputChannel.close();
        fileInputStream.close();
        fileOutputStream.close();
    }


}
