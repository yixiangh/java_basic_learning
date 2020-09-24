package com.example.javaFile.javaIo.nio;

import java.nio.IntBuffer;

public class BaseBuffer {
    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(5);
        System.out.println("buffer长度为："+intBuffer.capacity());
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i);
        }

        intBuffer.flip();   //对buffer进行读写转换（否则读不出数据）
        System.out.println(intBuffer.hasRemaining());//判断是否读取到末尾
        while (intBuffer.hasRemaining())
        {
            System.out.println(intBuffer.get());
        }
//        intBuffer.get();
    }
}
