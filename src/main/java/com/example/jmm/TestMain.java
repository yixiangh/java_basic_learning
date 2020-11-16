package com.example.jmm;

import java.util.ArrayList;
import java.util.List;

/**
 * JVM内存模型(JMM)
 * @Author: HYX
 * @Date: 2020/11/9 17:11
 */
public class TestMain {

    static int outNum = 200;
    public static void main(String[] args) {
//        ThreadA threadA = new ThreadA();
//        threadA.start();
        addUp();
//        method1();
//        System.out.println(outNum);
//        printMsg();
//        mockStackOverflowError();
        mockOutOfMemoryError();

    }


    public static void method1()
    {
        System.out.println("method1()开始执行");
        method2();
        System.out.println("method1()结束执行");
    }

    public static void method2()
    {
        System.out.println("method2()开始执行");
        String i = method3("100");
        System.out.println("method2()即将执行,i="+i);
    }

    public static String method3(String param)
    {
        System.out.println("method3()开始执行");
        String i = param;
        Long num = new Long(10L);
        System.out.println("method3()即将执行");
        return i;
    }

    public static void addUp()
    {
        int i = 1;
        int j = 2;
        int k = i + j;
        System.out.println(k);
    }


    public static void printMsg()
    {
        //当前JVM占用的内存总数，其值相当于当前JVM已使用的内存及freeMemory()的总和
        long totalMemory = Runtime.getRuntime().totalMemory()/1024/1024;
        //JVM堆的最大内存
        long maxMemory = Runtime.getRuntime().maxMemory()/1024/1024;
        //当前JVM空闲内存
        long freeMemory = Runtime.getRuntime().freeMemory()/1024/1024;
        System.out.println("当前使用JVM堆大小-Xms："+totalMemory+"M");
        System.out.println("当前JVM空闲内存："+freeMemory+"M");
        System.out.println("JVM堆的最大内存-Xmx："+maxMemory+"M");
        System.out.println("系统内存大小："+totalMemory*64/1024+"G");
        System.out.println("系统内存大小："+maxMemory*4/1024+"G");
    }

    /**
     * 模拟堆栈溢出
     */
    public static void mockStackOverflowError()
    {
        mockStackOverflowError();
    }

    /**
     * 模拟内存溢出
     */
    public static void mockOutOfMemoryError()
    {
        List<Object> list = new ArrayList<>();
        while (true)
        {
            int[] index = new int[20_000_000];
            list.add(index);
        }
    }



}
