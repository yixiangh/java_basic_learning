package com.example.javaThread;

/**
 * 线程基础
 * 线程的创建方式：继承Thread和实现runnable接口
 * sleep()暂停线程执行、不会释放锁
 * join()合并线程、
 * yield()使线程让步、不会释放锁，与sleep()和join()不同的是线程执行yield()后会进入Runnable(就绪状态)，而sleep()和join()会使线程进入阻塞状态
 */
public class JavaThread1 {


    public static void main(String[] args) {
        Runnable1 runnable1 = new Runnable1();
        Thread thread = new Thread(runnable1);
        thread.start();
//        try {
//            thread.join();//合并线程 原本是两个线程独立交替运行，现在使用join是runnable1线程和main线程合并，像一条线程一样运行，结果就是得等到runnable1线程运行完毕才能运行main线程
//        }catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        for (int i = 0; i < 100; i++) {
            System.out.println("main Thread=====" + i);
        }
    }
}

class Runnable1 implements Runnable {
    public void run() {
        Thread.currentThread().setName("Runnable1");//设置线程名称
        try {
            for (int i = 0; i < 100; i++) {
                if (i == 50) {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + "线程等待");
                }

                System.out.println("runnable1 Thread=====" + i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}