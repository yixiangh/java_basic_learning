package com.example.jmm;

/**
 * 线程A
 * @Author: HYX
 * @Date: 2020/11/9 17:26
 */
public class ThreadA extends Thread{

    @Override
    public void run() {
        int a = 10;
        int b = 20;
        int c = a + b;
        System.out.println(c);
    }
}
