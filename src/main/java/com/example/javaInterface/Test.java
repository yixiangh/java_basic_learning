package com.example.javaInterface;

public class Test {

    /**
     * 多态之接口
     */
    public static void test1() {
        Singer s1 = new Student("张三");
        s1.sign();
        s1.sleep();
        Singer s2 = new Student("李四");
        Dancer d1 = (Dancer) s2;
        d1.dance();
        d1.hiphop();
    }

    public static void test2() {
        ComparableAA c1 = new ComparableAA("张三", 18);
        ComparableAA c2 = new ComparableAA("李四", 20);
//        System.out.println(c2.compareTo(c1));
        String str1 = "100";
        String str2 = "200";
        System.out.println(str2.compareTo(str1));
    }

    public static void main(String[] args) {
        test1();
//        test2();
    }
}
