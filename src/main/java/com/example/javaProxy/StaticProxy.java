package com.example.javaProxy;

/**
 * 什么是代理模式(Proxy)
 * 定义：给目标对象提供一个代理对象，并由代理对象控制对目标对象的引用
 * java静态代理
 * 静态代理在使用时,需要定义接口或者父类,被代理对象与代理对象一起实现相同的接口或者是继承相同父类.
 * 首先定义一组接口Sell，用来提供广告和销售等功能。然后提供Vendor类（厂商，被代理对象）和Shop（超市，代理类），它们分别实现了Sell接口。
 */
public class StaticProxy {
    public static void main(String[] args) {
        // 供应商---被代理类
        Vendor vendor = new Vendor();

        // 创建供应商的代理类Shop
        Sell sell = new Shop(vendor);

        // 客户端使用时面向的是代理类Shop。
        sell.ad();
        sell.sell();
    }
}

interface Sell {
    /**
     * 出售
     */
    void sell();

    /**
     * 广告
     */
    void ad();
}

/**
 * 供应商-被代理对象
 */
class Vendor implements Sell {

    @Override
    public void sell() {
        System.out.println("供应商销售商品");
    }

    @Override
    public void ad() {
        System.out.println("供应商打广告");
    }
}

/**
 * 超市-代理类
 */
class Shop implements Sell {

    private Sell sells;

    public Shop(Sell sell) {
        this.sells = sell;
    }

    @Override
    public void sell() {
        System.out.println("超市在销售");
        sells.sell();
    }

    @Override
    public void ad() {
        System.out.println("超市在打广告");
        sells.ad();
    }
}
