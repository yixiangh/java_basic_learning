package com.example.javaLambda;

import com.example.entity.Student;
import com.sun.corba.se.spi.ior.ObjectKey;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Stream;

/**
 * lambda表达式
 * 语法格式：(parameters) -> expression或者(parameters) -> {statements}
 * Lambda表达式由三部分组成：
 * 1、paramaters：类似方法中的形参列表，这里的参数是函数式接口里的参数。这里的参数类型可以明确的声明也可不声明而由JVM隐含的推断，另外当只有一个推断类型时可以省略掉圆括号。
 * 2、->：可理解为“被用于”的意思
 * 3、方法体：可以是表达式也可以代码块，是函数式接口里方法的实现。代码块可返回一个值或者什么都不返回，这里的代码块块等同于方法的方法体。如果是表达式，也可以返回一个值或者什么都不反回。
 */
public class LambdaTest {

    public static List<String> methoda() {
        List<String> strList = new ArrayList<>();
        strList.add("张三");
        strList.add("李四");
        strList.add("王五");
        strList.add("赵六");
        return strList;
    }

    public static void methodb() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> entity = new HashMap<>();
            entity.put("id",i);
            if (i % 2==0)
            {
                entity.put("name",null);
            }
            entity.put("name",i+"号");
            list.add(entity);
        }
        System.out.println("原list集合大小为："+list.size());
        System.out.println("原list集合："+list.toString());
        Stream<Map<String, Object>> name = list.stream().filter(e -> StringUtils.isNotBlank(e.get("name").toString()));
    }

    public static void main(String[] args) {

        List<String> strList = methoda();
        strList.forEach(str -> System.out.println(str));//lambda表达式的方式遍历list

        Student stu = new Student();
        stu.setId(1234);
        stu.setName("张三");
        stu.setAge(12);
        stu.setSex("男");
        String aa = Optional.ofNullable(stu).map(e -> e.getName()).orElse("如果Name为空取这个值");
        System.out.println(aa);

        Map<String,String> map = new HashMap<>();
        map.put("张三","12345678901");
        map.put("李四","45678901231");
        map.forEach((name,phone)-> System.out.println(name+"========"+phone));
    }
}
