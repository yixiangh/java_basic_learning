package com.example.encodedecode;

import io.netty.util.CharsetUtil;

/**
 * java-编解码
 * @Author: HYX
 * @Date: 2020/7/23 15:15
 */
public class EncodeTest {

    public static void main(String[] args) {
        stringEncodeDecode();
    }

    public static void stringEncodeDecode()
    {
        String name = " ";
        byte[] bytes = name.getBytes(CharsetUtil.UTF_8);
//        System.out.println("字节长度："+bytes.length+"===="+bytes.toString());
        String aa = "5";
//        System.out.println(aa & 1234);

    }

    public static void charTest()
    {
        char aa = 'a';
        System.out.println();
    }



}
