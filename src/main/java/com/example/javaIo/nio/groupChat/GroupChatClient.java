package com.example.javaIo.nio.groupChat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

public class GroupChatClient {
    private String HOST = "127.0.0.1";
    private static final int PORT = 8989;
    private Selector selector;
    private SocketChannel socketChannel;
    private String userName = null;

    public static void main(String[] args) {
        //启动客户端
        GroupChatClient groupChatClient = new GroupChatClient();
        //启动一个线程，处理服务器发过来的消息
        new Thread(){
            @Override
            public void run() {
                while (true)
                {
                    groupChatClient.readMsg();
                    try {
                        Thread.currentThread().sleep(3000);
                    }catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        //主线程用于向服务器/其他客户端发送消息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine())//
        {
            String str = scanner.nextLine();
            groupChatClient.sendMsgToServer(str);
        }
    }







    /**
     * 客户端初始化
     */
    public GroupChatClient()
    {
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open(new InetSocketAddress(HOST,PORT));
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
            userName = socketChannel.getLocalAddress().toString().substring(1);
            System.out.println("用户："+userName+"上线了！");
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 向服务器发送消息
     */
    public void sendMsgToServer(String info)
    {
        info = userName+"说："+info;
        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 读取从服务器发过来的消息
     */
    public void readMsg()
    {
        try {
            int count = selector.select();
            if (count > 0)
            {
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                while (keyIterator.hasNext())
                {
                    SelectionKey key = keyIterator.next();
                    if (key.isReadable())
                    {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        channel.read(buffer);
                        String msg = new String(buffer.array());
                        System.out.println(msg.trim());
                    }else
                    {
                        System.out.println("没有事件发生的通道。。。。。");
                    }

                    keyIterator.remove();
                }
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }




}
