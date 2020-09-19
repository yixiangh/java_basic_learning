package com.example.javaProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 什么是代理模式(Proxy)
 * 定义：给目标对象提供一个代理对象，并由代理对象控制对目标对象的引用
 * java动态代理
 * 动态代理不需要实现接口,但是需要指定接口类型
 * 实现动态代理通常有两种方式：JDK原生动态代理和CGLIB动态代理。这里，我们以JDK原生动态代理为例来进行讲解。
 * JDK动态代理主要涉及两个类：java.lang.reflect.Proxy和java.lang.reflect.InvocationHandler。
 */
public class DynamicProxy {
    public static void main(String[] args) {
        Vendord vendord = new Vendord();
        DynamicHandler dynamicHandler = new DynamicHandler(vendord);
//        Selld selld = dynamicHandler.invoke((Selld) Proxy.newProxyInstance(Selld.class.getClassLoader()),new Class[]{Selld.class}, DynamicHandler.class);
//        selld.sell();
    }
}

//定义动态代理类
class DynamicHandler implements InvocationHandler {

    private Object target;//定义被代理对象的引用

    DynamicHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("超市销售商品");
        return null;
    }
}

interface Selld {
    void sell();
}

class Vendord implements Selld {
    @Override
    public void sell() {
        System.out.println("制造商在销售商品");
    }
}
