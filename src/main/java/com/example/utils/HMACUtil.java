package com.example.utils;

import io.netty.util.CharsetUtil;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * 散列消息鉴别码
 * 使用一个密钥生成一个固定大小的小数据块，即MAC，并将其加入到消息中，然后传输。接收方利用与发送方共享的密钥进行鉴别认证
 * @Author: HYX
 * @Date: 2020/12/9 9:42
 */
public class HMACUtil {

    /**
     * 加密方法（HmacMD5/HmacSHA1）
     */
    private static final String KEY_MAC = "HmacMD5";

    /**
     * 签名校验
     * @param content 签名原文
     * @param key 密钥
     * @param sign 签名
     * @return 验签结果
     */
    public static boolean verify(String content,String key,String sign) throws InvalidKeyException, NoSuchAlgorithmException {
        boolean flag = false;
        String encryptRes = encrypt(content,key);
        if (sign.equals(encryptRes))
        {
            flag = true;
        }
        return flag;
    }

    /**
     * 加密
     * 获取签名结果
     * @param content 待加密内容
     * @param key 密钥
     * @return 签名结果
     */
    public static String encrypt(String content,String key) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKey secretKey = new SecretKeySpec(Base64.getDecoder().decode(key),KEY_MAC);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        byte[] bytes = mac.doFinal(content.getBytes(CharsetUtil.UTF_8));
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * 初始化密钥
     * 根据密码初始化密钥
     * @return 密钥
     */
    public static String getMacKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);
        SecretKey secretKey = keyGenerator.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException {
        String content = "IOT评审会，确认方案是否可行";
        System.out.println("密钥："+getMacKey());
        String key = "4ANYv6aPUyYzRyXICJOc8GzvvrQQak9oYtYNu+yn7hrCFKnMJCbCvk/QD9iKmtpJ3OWHXswDICFF2utOpQ+CTA==";
        String encrypt = encrypt(content, key);
        System.out.println("加密结果："+encrypt);
        String sign = "tT1cDxD+Va+WeJ54tH0gHQ==";
        boolean bool = verify(content,key,sign);
        System.out.println("校验结果："+bool);
    }
}
