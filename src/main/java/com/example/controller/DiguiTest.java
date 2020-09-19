package com.example.controller;

public class DiguiTest {

    public static void main(String[] args) {
        System.out.println(method(5));
    }

    public static int method(int n) {
        if (n == 1) {
            return 1;
        } else {
            return n * method(n - 1);
        }
    }
}
