package com.example.utils;

import java.util.Random;

/**
 * 随机数
 * @Author: HYX
 * @Date: 2020/12/17 11:37
 */
public class RandomUtil {

    /**
     * 随机生成指定长度的字符串（字母+数组组成）
     * @param length 生成结果的长度
     * @param capital 字符是否转为大写
     * @return
     */
    public static String randomString(int length,boolean capital)
    {
        StringBuffer val = new StringBuffer();
        Random random = new Random();
        //参数length，表示生成几位随机数
        for(int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母
                int temp;
                if (capital)
                {
                    temp = 65;
                }else
                {
                    temp = 97;
                }
                val.append((char)(random.nextInt(26) + temp));
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                val.append(random.nextInt(10));
            }
        }
        return val.toString();
    }

    /**
     * 随机生成指定长度的字符串（字母+数组组成）
     * 字符大小写随机
     * @param length 生成结果的长度
     * @return
     */
    public static String randomString(int length)
    {
        StringBuffer val = new StringBuffer();
        Random random = new Random();
        //参数length，表示生成几位随机数
        for(int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val.append((char)(random.nextInt(26) + temp));
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                val.append(random.nextInt(10));
            }
        }
        return val.toString();
    }

}
