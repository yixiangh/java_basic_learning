package com.example.javaEnum;

import java.util.ArrayList;

/**
 * 枚举的使用
 */
public class EnumTest {
    public enum Color {
        red("红色", 1), green("绿色", 2), black("黑色", 3);

        private String name;
        private int val;
        Color(String name, int val) {
            this.name = name;
            this.val = val;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }
    }

    ;//定义枚举类型 相当于定义了一个类 Color，类中有三个静态变量red green black

    public static void main(String[] args) {
        for (Color color:Color.values()) {
            System.out.println(color.getName());
            System.out.println(color.getVal());
        }
//        new ArrayList<String>(1){{add("李四");}};
        System.out.println(Color.red);
    }
}
