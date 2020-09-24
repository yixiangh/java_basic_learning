package com.example.javaFile.javaIo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * nio通信-服务端
 * ServerSocketChannel相当于Socket中的ServerSocket,表示开启一个服务端Socket
 * nio分为selector单线程模式/reactor模式
 *
 *
 *
 */
public class NioServer {

    public static void main(String[] args) throws IOException {
        serverSocket();
    }

    public static void serverSocket() throws IOException {
        //获取选择器Selector
        Selector selector = Selector.open();
        //创建ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //设置ServerSocketChannel为非阻塞模式，使用selector选择器必须设置为非阻塞模式
        serverSocketChannel.configureBlocking(false);
        //创建监听地址 对象并绑定到ServerSocketChannel
        InetSocketAddress addr = new InetSocketAddress(8899);
        serverSocketChannel.bind(addr);
        //讲ServerSocketChannel注册到selector选择器并设置其关注的事件
        //SelectionKey.OP_CONNECT   ：连接就绪
        //SelectionKey.OP_ACCEPT    ：接收就绪
        //SelectionKey.OP_READ      ：读就绪
        //SelectionKey.OP_WRITE     ：写就绪
        //ServerSocketChannel绑定到selector选择器后会返回一个SelectionKey
        //SelectionKey理解为与一个通道/事件对应，可以根据selectionKey反向获取对应通道或者事件信息
        SelectionKey selectKy = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //循环遍历selector选择器，判断是否有事件已经就绪
        while (true)
        {
            //select返回有事件发生的通道的个数，select()参数表示多久执行一次，默认为空，阻塞状态，表示一直阻塞在这里，比如1000表示阻塞1秒执行一次
            int noOfKeys = selector.select(5000);
            System.out.println("目前有事件发生的通道个数为："+noOfKeys);
            if (noOfKeys == 0) //select（）方法返回当前有多少事件就绪，也就是说有多少通道channel中发生了事件 参数1000表示等待1秒
            {
                System.out.println("等待了1秒，没有连接过来。。。。。。。");
                continue;
            }
            System.out.println("有连接进来了");
            //如果select方法返回大于0，表示有事件过来，通过selectedKeys()获取所有事件集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            System.out.println("selectionKeys集合======="+selectionKeys.size());
            //使用迭代器遍历selectedKeys集合可以获取每个通道发生的事件
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext())
            {
                SelectionKey key = iterator.next(); //获取每个通道发生的事件
                //需要手动将当前selectionKey从集合中移除，防止重复操作
                iterator.remove();
                if (key.isAcceptable()) //判断事件所属类型，这个表示是OP_ACCEPT事件，表示有客户端连接过来了
                {
                    System.out.println("当前事件类型为：OP_ACCEPT");
                    //为该客户端生成一个通道，并将通道也注册到selector选择器上
                    ServerSocketChannel serverSocketChannel1 = (ServerSocketChannel) key.channel();
                    SocketChannel clientChannel = serverSocketChannel1.accept();
                    //设置通道为非阻塞模式
                    clientChannel.configureBlocking(false);
                    //给客户端返回信息
                    clientChannel.write(ByteBuffer.wrap(new String("欢迎你："+clientChannel.getRemoteAddress().toString().substring(1)).getBytes()));
                    //在注册的同时，表明该通道关注的事件，这里设置为读取也就是读就绪
                    clientChannel.register(selector,SelectionKey.OP_READ);
                }else if (key.isReadable())   //表示是OP_READ事件
                {
                    System.out.println("当前事件类型为：OP_READ");
                    readMsg(key);
                }

            }

        }
    }

    /**
     * 这个是selector单线程模型
     * reactor模型为在读取数据这一块提供要给线程池，使用多线程去读取
     * @param key
     * @throws IOException
     */
    public static void readMsg(SelectionKey key) throws IOException {
        //由于我们上边将客户端的链接请求重新分配了一个关注读事件的通道，所以在这里可以获取到
        //根据SelectionKey反向获取通道
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //由于我们上边已经给该channel通道分配了Buffer,所以这里可以重新获取
        //selectionKey也可以反向获取其buffer，用attachment方法
//                    ByteBuffer buffer = (ByteBuffer) key.attachment();
        //讲channel信息写入到buffer中
        channel.read(buffer);
        System.out.println("客户端发来的消息："+new String(buffer.array()));
    }


}
