package com.example.abstractClass;

/**
 * 子类
 * @Author: HYX
 * @Date: 2020/7/10 15:20
 */
public class SubClass extends SuperClass {

    static {
        System.out.println("子类静态代码块执行了");
    }

    public SubClass()
    {
        System.out.println("子类无参构造方法执行了");
    }

    public static void staticMethed()
    {
        System.out.println("子类静态方法执行了");
    }

    @Override
    public void absMetehed() {
        System.out.println("子类继承过来的抽象方法执行了");
    }


}
