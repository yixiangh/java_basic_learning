package com.example.abstractClass;

/**
 * 父类
 * @Author: HYX
 * @Date: 2020/7/10 15:14
 */
public abstract class SuperClass {

    static {
        System.out.println("父类静态代码块执行了");
    }
    public SuperClass()
    {
        System.out.println("父类无参构造方法执行了");
    }

    public static void staticMethed()
    {
        System.out.println("父类静态方法执行了");
    }

    public abstract void absMetehed();

}
