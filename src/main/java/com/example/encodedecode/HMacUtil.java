package com.example.encodedecode;

import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

/**
 * HMAC是密钥相关的哈希运算消息认证码（Hash-based Message Authentication Code）
 * HMAC 运算利用 哈希算法 (MD5、SHA1 等)，以 一个密钥 和 一个消息 为输入，生成一个 消息摘要 作为 输出。
 * HMAC 发送方 和 接收方 都有的 key 进行计算，而没有这把 key 的第三方，则是 无法计算 出正确的 散列值的，这样就可以 防止数据被篡改。
 * @Author: HYX
 * @Date: 2020/10/16 14:29
 */
@Slf4j
public class HMacUtil {

    /**
     * MAC算法可选以下多种算法
     * HmacMD5/HmacSHA1/HmacSHA256/HmacSHA384/HmacSHA512
     */
    private static final String KEY_MAC = "HmacMD5";

    public static void main(String[] args) {
        String key = "qwertyuiop";
        String content = "{name:'admin',password:'admin123456'}";
        String result = encry(key,content); //7RxDX0XEzuWz2nL0GUf39g==
        boolean verify = verify(key, result, content);
        System.out.println("验证结果："+verify);
    }


    /**
     * 加密
     * @param key 密钥
     * @param content 待加密内容
     * @return 加密后的结果
     */
    public static String encry(String key,String content)
    {
        try {
            SecretKey secretKey = new SecretKeySpec(key.getBytes(CharsetUtil.UTF_8),KEY_MAC);
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            byte[] bytes = mac.doFinal(content.getBytes());
            String result = Base64.getEncoder().encodeToString(bytes);
            log.info("加密结果：{}",result);
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 验签
     * @param key 密钥
     * @param signature 签名
     * @param content 原数据
     * @return 验证结果
     */
    public static boolean verify(String key,String signature,String content)
    {
        try {
            SecretKey secretKey = new SecretKeySpec(key.getBytes(CharsetUtil.UTF_8),KEY_MAC);
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            byte[] bytes = mac.doFinal(content.getBytes());
            return Arrays.equals(bytes,Base64.getDecoder().decode(signature));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return false;
    }
}
