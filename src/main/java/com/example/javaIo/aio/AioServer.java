package com.example.javaIo.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * Aio服务端-异步非阻塞
 * @Author: HYX
 * @Date: 2020/7/11 11:05
 */
public class AioServer {

    public static void main(String[] args) {

    }

    public static void aioserverMain() throws IOException, InterruptedException {
        final AsynchronousServerSocketChannel serverSocketChannel = AsynchronousServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8989));
        //这里的accept方法不会阻塞，此方法作用是创建一个CompletionHandler方法交给操作系统，由系统在接到客户端连接时，
        //去执行这个方法里的逻辑
        serverSocketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            @Override
            public void completed(AsynchronousSocketChannel client, Object attachment) {
                serverSocketChannel.accept(null,this);
                try {
                    System.out.println("远程客户端地址："+client.getRemoteAddress());
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    //这里的read读方法也不会阻塞，同样的会创建一个CompletionHandler方法，交给系统，
                    // 系统在接收到客户端发送的消息后会执行CompletionHandler方法处理消息
                    client.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                        @Override
                        public void completed(Integer result, ByteBuffer attachment) {
                            attachment.flip();//读写转换
                            System.out.println("客户端发来的消息："+new String(attachment.array(),0,result));
                            //向客户端写数据
                            client.write(ByteBuffer.wrap("你好，客户端！".getBytes()));
                        }
                        @Override
                        public void failed(Throwable exc, ByteBuffer attachment) {
                            System.out.println("服务器出现异常！");
                            exc.printStackTrace();
                        }
                    });
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //lian连接失败，调用
            @Override
            public void failed(Throwable exc, Object attachment) {

            }
        });
        while (true)
        {
            Thread.sleep(1000);
        }
    }


}
