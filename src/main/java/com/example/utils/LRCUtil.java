package com.example.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * LRC校验
 * 纵向冗余校验（Longitudinal Redundancy Check，简称：LRC）是通信中常用的一种校验形式，也称LRC校验或纵向校验。
 * 它是一种从纵向通道上的特定比特串产生校验比特的错误检测方法。在行列格式中（如磁带），
 * LRC经常是与VRC一起使用，这样就会为每个字符校验码。在工业领域Modbus协议Ascii模式采用该算法。
 * 具体算法如下：
 * 1、对需要校验的数据（2n个字符）两两组成一个16进制的数值求和。
 * 2、将求和结果与256求模。
 * 3、用256减去所得模值得到校验结果（另一种方法：将模值按位取反然后加1）。
 * @Author: HYX
 * @Date: 2020/12/17 17:02
 */
public class LRCUtil {

    private static final String CHARSET_NAME = "UTF-8";
    /**
     * 生成LRC校验码（求模方式）
     * @param content 内容
     * @return 生成的校验位值（16进制）
     */
    public static String lrc(String content)
    {
        if (content != null && content != "")
        {
            byte[] contentBytes = content.getBytes(Charset.forName("UTF-8"));
            int sum = 0;
            for (int i = 0; i < contentBytes.length; i++) {
                Integer contentByte = Integer.valueOf(contentBytes[i]);
                sum+=contentByte;
            }
            int res = 256 - (Math.floorMod(sum,256));
            String toHexString = Integer.toHexString(res);
            return toHexString;
        }
        return null;
    }

    /**
     * 生成LRC校验码（与或运算方式）
     * @param content
     * @return
     */
    public static int getlrc(String content)
    {
        if (content != null && content != "")
        {
            try {
                byte[] contentBytes = content.getBytes(CHARSET_NAME);
                byte lrc = 0;
                for(int i=0;i<contentBytes.length;i++)
                {
                    System.out.print("contentBytes:"+contentBytes[i]+" ");
                    System.out.print("lrc:"+lrc+" ");
                    lrc ^= contentBytes[i];
                    System.out.print("res:"+lrc+" ");
                    System.out.println();
                }
                return lrc;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        String content = "AB12KJFG2a87J8";
        String res = lrc(content);
        System.out.println(res);

        int getlrc = getlrc(content);
        System.out.println();
        System.out.println(getlrc);

    }
}
