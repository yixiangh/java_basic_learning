package com.example.javaThread;

/**
 * 线程同步
 * synchronized:锁定当前对象（锁定某一段代码），使其只能被一个线程访问   这个是互斥锁
 *
 */
public class JavaThread2 implements Runnable {

    Timer timer = new Timer();

    @Override
    public void run() {
        timer.add(Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        JavaThread2 javaThread2 = new JavaThread2();
        Thread thread1 = new Thread(javaThread2);
        Thread thread2 = new Thread(javaThread2);
        thread1.setName("thread-1");
        thread2.setName("thread-2");
        thread1.start();
        thread2.start();
    }
}

class Timer {
    public int num = 0;

    public synchronized void add(String threadName) {
//       synchronized (this){
        num++;
        System.out.println("num=" + num);
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(threadName + "你是第" + num + "个使用timer的线程");
    }
//   }

}