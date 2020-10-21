package com.example.encodedecode;

import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * AED对称加密（是DES的增强版）
 * @Author: HYX
 * @Date: 2020/10/16 11:17
 */
@Slf4j
public class AESUtil {
    /**
     * 密钥算法
     */
    private static String KEY_ALGORITHM = "AES";

    public static void main(String[] args) {
        String seed = "qwertyuiop";   //YGPHhFa7BIuvNr4RBNEtVA==
        String content = "{name:'张三',password:'pwd123456'}";
        String key = generateKey(seed);
        String res = encrypt(key,content);
        decrypt(res,key);
    }

    /**
     * 获取密钥
     * @param seed 种子数据
     * @return 密钥
     */
    public static String generateKey(String seed)
    {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(seed.getBytes(CharsetUtil.UTF_8));
            keyGenerator.init(128,secureRandom);
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] encoded = secretKey.getEncoded();
            String key = Base64.getEncoder().encodeToString(encoded);
            log.info("密钥："+key);
            return key;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 加密
     * @param key 密钥
     * @param content 待加密内容
     * @return 加密后结果
     */
    public static String encrypt(String key,String content)
    {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.getDecoder().decode(key),KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec);
            byte[] bytes = cipher.doFinal(content.getBytes());
            String res = Base64.getEncoder().encodeToString(bytes);
            log.info("加密结果：{}",res);
            return res;
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     * @param ciphertext 加密后的密文
     * @param key 密钥
     * @return 解密后的结果
     */
    public static String decrypt(String ciphertext,String key)
    {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.getDecoder().decode(key),KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE,secretKeySpec);
            byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
            log.info("解密后的结果：{}",new String(bytes,CharsetUtil.UTF_8));
            return new String(bytes,CharsetUtil.UTF_8);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }




}
