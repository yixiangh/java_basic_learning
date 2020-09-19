package com.example.javaInterface;

public class ComparableAA implements Comparable {

    private String name;
    private int age;

    ComparableAA(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
