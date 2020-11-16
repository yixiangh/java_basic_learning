package com.example.controller;

import com.example.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping(value = "test1")
@Slf4j
public class Test {

    @Autowired
    private ComponentProperties componentProperties;

    public static void main(String[] args) {
        hello();
    }

    public static void hello()
    {
        LogTest.logOut();
        log.trace("test -> hello -> trace");
        log.debug("test -> hello -> debug");
        log.info("test -> hello -> info");
        log.warn("test -> hello -> warn");
        log.error("test -> hello -> error");
//        log.fatal("test -> hello -> info");
    }


    private void convert(List<Student> stuList)
    {
        Student stu = new Student();
        stu.setId(1234);
        stu.setSex("男");
        stu.setAge(18);
        stu.setName("张三");
        stuList.add(stu);
    }

    public static void returnByteNum()
    {
        int intSize = Integer.SIZE;
        System.out.println("int size:"+(intSize/8)+"Byte");
        int shortSize = Short.SIZE;
        System.out.println("short size: " +(shortSize/8) + "Byte" );
        int longSize = Long.SIZE;
        System.out.println("long size: " + (longSize/8) + "Byte" );
        int byteSize = Byte.SIZE;
        System.out.println("byte size: " + (byteSize/8) + "Byte" );
        int floatSize = Float.SIZE;
        System.out.println("float size: " + (floatSize/8) + "Byte" );
        int doubleSize = Double.SIZE;
        System.out.println("double size: " + (doubleSize/8) + "Byte" );
        int charSize = Character.SIZE;
        System.out.println("char size: " + (charSize/8) + "Byte" );
    }

//    public static void main(String[] args) {
//        Cat cat = new Cat("小花","blue");//new一只猫
//        Dog dog = new Dog("小黑","black");//new一只狗
//        Zoo zoo1 = new Zoo("大动物园",cat);//将猫放进动物园
//        Zoo zoo2 = new Zoo("大动物园",dog);//将狗放进动物园
//        zoo1.animalEnjoy();//猫开始叫了
//        zoo2.animalEnjoy();//狗开始叫了
//    }
}

////定义一个  动物园  类
//class Zoo{
//    private String name;                //动物园名称
//    private Animal animal;              //定义动物对象，动物园有动物
//    Zoo(String name,Animal animal){     //定义带参构造方法
//        this.name = name;
//        this.animal = animal;
//    };
//    public void animalEnjoy()           //动物园的动物叫的方法
//    {
//        animal.enjoy();                 //调用动物对象的叫的方法
//    }
//}
//
////定义一个  动物   类
//class Animal{
//    private String name;
//    Animal(){};                         //声明无参构造方法
//    Animal(String name){                //声明带参构造方法
//        this.name = name;
//    };
//    public void enjoy()                 //定义  动物吃食物  的方法
//    {
//        System.out.println("吃东西。。。。。。");
//    }
//}
////定义  猫  对象  并继承自 动物
//class Cat extends Animal{
//
//    private String eyesColor;           //眼睛颜色
//    Cat(String name,String eyesColor){  //定义带参构造方法
//        super(name);                    //调用父类的带参构造方法 将名称传递过去
//        this.eyesColor = eyesColor;
//    };
//    @Override
//    public void enjoy() {               //重写父类叫的方法，单独为猫设置吃食物
//        System.out.println("猫吃食物。。。。。");
//    }
//}
////定义  狗  对象  并继承自  动物
//class Dog extends Animal{
//    private String furColor;//毛发颜色
//    Dog(String name,String furColor){   //定义带参构造方法
//        super(name);                    //调用父类的带参构造方法 将名称传递过去
//        this.furColor = furColor;
//    };
//    @Override
//    public void enjoy() {               //重写父类叫的方法，单独为狗设置吃食物
//        System.out.println("狗吃食物。。。。。");
//    }
//}
