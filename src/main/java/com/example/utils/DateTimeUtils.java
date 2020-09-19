package com.example.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 日期时间公共方法-java8
 * @Author: HYX
 * @Date: 2020/7/31 14:46
 */
public class DateTimeUtils {

    private static final String DATE_PATTERN = "YYYY-MM-dd";
    private static final String DATE_TIME_PATTERN = "YYYY-MM-dd HH:mm:ss";



    /**
     * 返回当前日期-String类型
     * @return
     */
    public static String getNowDate()
    {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        return localDate.format(dateTimeFormatter);
    }

    /**
     * 返回当前日期-String类型
     * @return
     */
    public static String getNowDateTime()
    {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("测试git提交回滚");
        return null;
    }

}
