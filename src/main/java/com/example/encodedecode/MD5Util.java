package com.example.encodedecode;

import io.netty.util.CharsetUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * MD5加密-信息摘要
 * @Author: HYX
 * @Date: 2020/10/16 17:48
 */
public class MD5Util {

    public static void main(String[] args) {
//        // 原密码
//        String plaintext = "123456";
//        // 获取加盐后的MD5值
//        String ciphertext = getSaltMD5(plaintext);
//        System.out.println("加盐后MD5：" + ciphertext);
//        System.out.println("是否是同一字符串:" + verifySaltMD5(plaintext, ciphertext));


    }

    /**
     * MD5加密
     * 结果转为16进制字符串
     * @param content 待加密内容
     * @return 加密后的16进制字符串
     */
    public static String md5Hex(String content)
    {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] digest = messageDigest.digest(content.getBytes(CharsetUtil.UTF_8));
            return hex(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * MD5加盐加密
     * @param content 待加密内容
     * @return 加盐加密后的结果
     */
    public static String getSaltMD5(String content)
    {
        // 生成一个16位的随机数
        Random random = new Random();
        StringBuilder sBuilder = new StringBuilder(16);
        sBuilder.append(random.nextInt(99999999)).append(random.nextInt(99999999));
        int len = sBuilder.length();
        if (len < 16) {
            for (int i = 0; i < 16 - len; i++) {
                sBuilder.append("0");
            }
        }
        // 生成最终的加密盐
        String salt = sBuilder.toString();
        content = md5Hex(content + salt);
        char[] cs = new char[48];
        for (int i = 0; i < 48; i += 3) {
            cs[i] = content.charAt(i / 3 * 2);
            char c = salt.charAt(i / 3);
            cs[i + 1] = c;
            cs[i + 2] = content.charAt(i / 3 * 2 + 1);
        }
        return String.valueOf(cs);
    }


    /**
     * 验证加盐后的结果是否和原内容一致
     * @param content 原内容
     * @param md5Content 加盐加密后的值
     * @return 验证结果
     */
    public static boolean verifySaltMD5(String content,String md5Content)
    {
        char[] cs1 = new char[32];
        char[] cs2 = new char[16];
        for (int i = 0; i < 48; i += 3) {
            cs1[i / 3 * 2] = md5Content.charAt(i);
            cs1[i / 3 * 2 + 1] = md5Content.charAt(i + 2);
            cs2[i / 3] = md5Content.charAt(i + 1);
        }
        String salt = new String(cs2);
        return md5Hex(content + salt).equals(String.valueOf(cs1));
    }

    /**
     * byte字节数组 转 16进制字符串
     * @param arr byte字节数组
     * @return 16进制字符串
     */
    public static String hex(byte[] arr)
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1,3));
        }
        return sb.toString();
    }
}
