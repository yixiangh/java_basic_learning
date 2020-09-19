package com.example.javaInterface;

public class Student implements Singer, Dancer {

    private String name;

    Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void study() {
        System.out.println("学生开始学习");
    }

    @Override
    public void sign() {
        System.out.println("学生开始唱歌");
    }

    @Override
    public void sleep() {
        System.out.println("学生开始睡觉");
    }

    @Override
    public void dance() {
        System.out.println("学生在跳舞");
    }

    @Override
    public void hiphop() {
        System.out.println("学生在跳街舞");
    }
}
