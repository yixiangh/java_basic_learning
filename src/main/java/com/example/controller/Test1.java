package com.example.controller;

public class Test1 {

    public static void main(String[] args) {
        Cat cat = new Cat("小花", "花色");//new一只猫
        Dog dog = new Dog("小黑", "藏獒");//new一只狗
        Zoo zoo1 = new Zoo("大动物园");//将猫放进动物园
        zoo1.eat(cat);//猫开始吃东西
        zoo1.eat(dog);//猫开始吃东西
    }
}

//定义一个  动物园  类
class Zoo {
    private String name;                //动物园名称

    Zoo(String name) {                   //定义带参构造方法
        this.name = name;
    }

    ;

    public void eat(Animal animal)           //动物园的猫开始吃东西
    {
        animal.eat();                    //调用猫的吃东西的方法
    }
}

//定义一个  动物   类
class Animal {
    private String name;

    Animal(String name) {                //声明带参构造方法
        this.name = name;
    }

    ;

    public void eat()                 //定义  动物吃食物  的方法
    {
        System.out.println("吃东西。。。。。。");
    }
}

//定义  猫  对象
class Cat extends Animal {

    private String color;           //猫的颜色

    Cat(String name, String color) {  //定义带参构造方法，传入猫的名称和特征
        super(name);
        this.color = color;
    }

    ;

    @Override
    public void eat() {               //重写父类吃的方法，重新为小猫设置吃食物
        System.out.println("猫吃食物。。。。。");
    }
}

//定义  狗  对象
class Dog extends Animal {
    private String type;//种类

    Dog(String name, String type) {   //定义带参构造方法，传入狗的名称和特征
        super(name);
        this.type = type;
    }

    ;

    @Override
    public void eat() {               //重写父类吃的方法，重新为小狗设置吃食物
        System.out.println("狗吃食物。。。。。");
    }
}
