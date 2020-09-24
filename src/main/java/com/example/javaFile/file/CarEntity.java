package com.example.javaFile.file;

import java.io.Serializable;

/**
 * 汽车实体对象
 * @Author: HYX
 * @Date: 2020/9/24 10:18
 */
public class CarEntity implements Serializable {

    /**
     * 显式声明serialVersionUID的作用在于：
     * 对象被序列化后，即便我们修改了实体对象内容（比如，添加/删除字段等），只要serialVersionUID之没变，就可以反序列化
     * 如果由java默认帮我们生成的话，由于java是根据实体对象的内容/属性等生成的，因此只要稍微有一点变动，就会导致serialVersionUID不一致而发序列化失败
     */
    private static final long serialVersionUID = 1L;

    /**
     * 静态变量不支持序列化
     */
    public static String model = "RS";

    private String name;
    private String color;
    /**
     * transient 标注的字段不会被序列化
     */
    transient private Integer wheel;//轮子
    private String count;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getWheel() {
        return wheel;
    }

    public void setWheel(Integer wheel) {
        this.wheel = wheel;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "CarEntity{" +
                "count='" + count + '\'' +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", wheel=" + wheel + '\'' +
                ",model=" + model +
                '}';
    }
}
