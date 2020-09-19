package com.example.optional;

import java.util.Optional;

/**
 * java8 Optional使用
 * @Author: HYX
 * @Date: 2020/9/18 11:41
 */
public class OptionalTest {
    public static void main(String[] args) {
        optionalFunc();
        System.out.println("测试git提交5");
    }

    public static void optionalFunc()
    {
        // Optional类已经成为Java 8类库的一部分，在Guava中早就有了，可能Oracle是直接拿来使用了
        // Optional用来解决空指针异常，使代码更加严谨，防止因为空指针NullPointerException对代码造成影响
        String msg = "hello world";
        Optional<String> optional = Optional.ofNullable(msg);
        // 判断是否有值，不为空
        boolean present = optional.isPresent();
        System.out.println(present);
        // 如果有值，则返回值，如果等于空则抛异常
        String value = optional.get();
        // 如果为空，返回else指定的值
        String hi = optional.orElse("hi");
        // 如果值不为空，就执行Lambda表达式
        optional.ifPresent(opt -> System.out.println(opt));
    }
}
