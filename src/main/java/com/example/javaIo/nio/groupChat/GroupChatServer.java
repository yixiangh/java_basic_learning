package com.example.javaIo.nio.groupChat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * Nio学习-群聊系统服务端
 */
public class GroupChatServer {

    private Selector selector;  //定义selector选择器（多路复用器），用于检查一个或多个NIO Channel（通道）的状态是否处于可读、可写。如此可以实现单线程管理多个channels,也就是可以管理多个网络链接。
    private ServerSocketChannel serverSocketChannel;    //定义ServerSocketChannel 用于监听新进来的TCP连接的通道 就像标准IO中的ServerSocket一样
    private static final int PORT = 8989;   //定义Nio服务端监听端口


    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();//服务端开启监听
    }


    /**
     * 构造器-用于初始化工作
     */
    public GroupChatServer()
    {
        try {
            selector = Selector.open(); //打开一个Selector
            serverSocketChannel = ServerSocketChannel.open();//打开一个ServerSocketChannel
            serverSocketChannel.bind(new InetSocketAddress(PORT));//绑定端口号到ServerSocketChannel
            serverSocketChannel.configureBlocking(false);   //使用selector的话 必须设置为非阻塞模式
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT); //将ServerSocketChannel注册到Selector选择器上，并设置其关注的事件为 接收就绪
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 监听
     */
    public void listen()
    {
        try {
            //轮询访问selector
            while (true)
            {
                int count = selector.select(2000);//通过select()判断当前是否有通道发生事件，返回事件数
                if (count > 0)
                {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();//获取selectedKeys的迭代器
                    while (iterator.hasNext())//遍历SelectedKeys,得到每一个通道发生的事件，并判断事件类型，做出相应处理
                    {
                        SelectionKey selectionKey = iterator.next();
                        if (selectionKey.isAcceptable())    //如果是accept请求，就使用serverSocketChannel返回一个SocketChannel来处理
                        {
                            SocketChannel clientChannel = serverSocketChannel.accept();//使用serverSocketChannel返回一个SocketChannel来做具体处理
                            clientChannel.configureBlocking(false); //设置为非阻塞模式
                            clientChannel.register(selector,SelectionKey.OP_READ);//注册到selector并设置其关注的事件为read 读
                            System.out.println("用户："+clientChannel.getRemoteAddress()+"上线了！");
                        }else if (selectionKey.isReadable())    //如果是read请求，就执行readByte()对消息进行读取
                        {
                            readByte(selectionKey);//读取客户端消息
                        }else if (selectionKey.isConnectable())
                        {
                            System.out.println("我是-connectable");
                        }else if (selectionKey.isWritable())
                        {
                            System.out.println("我是-writable");
                        }else if (selectionKey.isValid())
                        {
                            System.out.println("我是-valid");
                        }
                        //移除集合中当前事件，防止重复处理
                        iterator.remove();
                    }

                }
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 读取客户端消息
     */
    private void readByte(SelectionKey key)
    {
        SocketChannel channel = null;
        try {
            channel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int readLength = channel.read(buffer);
            if (readLength > 0)
            {
                String msg = new String(buffer.array());
                System.out.println("客户端"+channel.getRemoteAddress()+"发来了消息："+msg);
                //向其他客户端转发消息
                sendMsgToOtherClients(msg,channel);
            }else
            {

            }
        }catch (IOException e)
        {
            try {
                System.out.println("用户"+channel.getRemoteAddress()+"离线了");
                key.cancel();//取消注册
                channel.close();//关闭通道
            }catch (IOException e1)
            {
                e1.printStackTrace();
            }
        }
    }

    /**
     * 服务器收到消息后转发消息给其他客户端，注意要排除自身
     * @param msg
     * @param self
     */
    private void sendMsgToOtherClients(String msg,SocketChannel self) throws IOException {
        System.out.println("服务器消息转发中。。。。。。");
        //selector.keys()返回的是所有注册在selector选择器上的发生事件的通道
        //遍历所有注册到selector上的SocketChannel,并排除自己
        for (SelectionKey key:selector.keys()) {
            Channel channel = key.channel();
            if (channel instanceof SocketChannel && channel != self)
            {
                SocketChannel socketChannel = (SocketChannel) channel;//
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                socketChannel.write(buffer);
            }
        }
    }




}
