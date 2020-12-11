package com.example.utils;

import io.netty.util.CharsetUtil;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * AES高级加密标准（对称加密）
 * @Author: HYX
 * @Date: 2020/12/8 14:19
 */
public class AESUtil {

    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";//默认的加密算法
    private static final String KEY_ALGORITHM = "AES";


    /**
     * 加密
     * @param content 待加密内容
     * @param password 加密需要的密码
     * @return 加密结果
     */
    public static String encrypt(String content,String password)
    {
        try {
            SecretKeySpec secretKey = getSecretKey(password);
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);//创建密码器
            cipher.init(Cipher.ENCRYPT_MODE,secretKey);//初始化为加密模式的密码器
            byte[] contentBytes = content.getBytes(CharsetUtil.UTF_8);
            byte[] bytes = cipher.doFinal(contentBytes);//加密
            String result = ByteUtil.byteToHex(bytes);//字节数组转16进制
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
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

    public static String decrypt(String content,String password)
    {
        try {
            SecretKeySpec secretKey = getSecretKey(password);
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);//创建密码器
            cipher.init(Cipher.DECRYPT_MODE,secretKey);//初始化为解密模式的密码器
            byte[] contentBytes = ByteUtil.hexStringToBytes(content);
            byte[] bytes = cipher.doFinal(contentBytes);//解密
            String result = new String(bytes,CharsetUtil.UTF_8);//字节数组转16进制
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
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

    /**
     * 获取密钥
     * @param password 加密需要的密码
     * @return 密钥
     * @throws NoSuchAlgorithmException
     */
    private static SecretKeySpec getSecretKey(String password) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);//创建AES的Key生产者
        //利用用户密码作为随机数初始化出,加密没关系，SecureRandom是生成安全随机数序列，password.getBytes()是种子，只要种子相同，序列就一样，所以解密只要有password就行
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(password.getBytes());
        //初始化为一个128位的随机源,根据传入的字节数组
        keyGenerator.init(128, random);
        SecretKey secretKey = keyGenerator.generateKey();//根据用户密码，生成一个密钥
        byte[] encodedKey = secretKey.getEncoded();//返回基本编码格式的密钥，如果此密钥不支持编码，则返回
        SecretKeySpec secretKeySpec = new SecretKeySpec(encodedKey,KEY_ALGORITHM);//转为AES专用密钥
        return secretKeySpec;
    }

    public static void main(String[] args) {
        String name = "my name is 张三";
        String encrypt = encrypt(name, "hyx1234");
        System.out.println(encrypt);
        System.out.println(encrypt.length());
        String decrypt = decrypt(encrypt, "hyx1234");
        System.out.println(decrypt);

    }

}
