package com.example.javaThread;

/**
 * 线程死锁
 * 死锁：两个或两个以上线程或进程在执行过程中，由于竞争资源或由于彼此通信而造成的一种阻塞现象
 *
 */
public class JavaThread3 {


    public static void main(String[] args) {
        Thread thread1 = new Thread(new DeadLock(true));
        Thread thread2 = new Thread(new DeadLock(false));
        thread1.setName("thread-1");
        thread2.setName("thread-2");
        thread1.start();
        thread2.start();
    }
}

class DeadLock implements Runnable {
    static Object obj1 = new Object();//声明一个对象1
    static Object obj2 = new Object();//声明一个对象2

    boolean lock;                    //定义boolean字符用于区分两个线程要执行的代码块，用于区分不同线程执行的内容

    DeadLock(boolean lock)           //构造方法
    {
        this.lock = lock;
    }

    //不同的线程根据传入的lock值不同 进而执行不同的内容
    //代码块1中先锁定obj1对象  如要继续执行需再锁定obj2
    //代码块2中先锁定obj2对象  如要继续执行需再锁定obj1
    //这样  由于两个线程都持有并锁定了对方所需的obj  因此两个线程都无法继续执行，进而只能阻塞  造成死锁
    @Override
    public void run() {
        if (lock)                                //代码块1
        {
            synchronized (obj1) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (obj2) {
                    System.out.println("我是obj");
                }
            }
        } else                                       //代码快2
        {
            synchronized (obj2) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (obj1) {
                    System.out.println("我是obj");
                }
            }
        }
    }
}