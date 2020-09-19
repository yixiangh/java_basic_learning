package com.example.javaThread;

import java.util.concurrent.*;

/**
 * java线程池
 */
public class ThreadPool {

    private static ExecutorService pool;

    public static void main(String[] args) throws InterruptedException {
//        pool = Executors.newCachedThreadPool();
        pool = Executors.newFixedThreadPool(10);
//        pool = new ThreadPoolExecutor();
        pool.execute(new Thread1());
        Thread.sleep(1000);
        System.out.println("这是main线程");
        pool.shutdown();
    }

}

class Thread1 implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "在运行");
    }
}
