package com.example.encodedecode;

import com.example.javaFile.file.CarEntity;
import io.netty.util.CharsetUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tomcat.util.buf.HexUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * java中的各种加密解密
 * 1.BASE64加密/解密
 * 2.MD5(Message Digest Algorithm)加密/解密
 * 3.DES(Data Encryption Standard)对称加密/解密
 * 4.AES（Advanced Encryption Standard） 加密/解密
 * 5.HMAC(Hash Message Authentication Code，散列消息鉴别码)
 * 6.恺撒加密
 * 7.SHA(Secure Hash Algorithm，安全散列算法)
 * 8.RSA 加密/解密
 * 9.PBE 加密/解密
 * @Author: HYX
 * @Date: 2020/9/25 14:52
 */
public class EncryptionAndDecryption {

    public static void main(String[] args) {
        String msg = "马上就要中秋节了";
        String secretKey = "01234567";//长度必须是8的倍数,即64bit长度
//        String encode = encryptBASE64(msg.getBytes());
//        decryBASE64(encode);
//        encryMD5(msg);
//        byte[] encryptData = desEncrypt(Cipher.ENCRYPT_MODE, msg.getBytes(), secretKey);
//        System.out.println("DES加密结果："+encryptData);
//        String decryptData = DES_CBC_Decrypt(encryptData, secretKey.getBytes());
//        System.out.println("DES解密结果："+decryptData);
        System.exit(0);
    }

    /**
     * Base64解密
     * @param key
     */
    public static void decryBASE64(String key)
    {
        BASE64Decoder base64Decoder = new BASE64Decoder();
        try {
            byte[] bytes = base64Decoder.decodeBuffer(key);
            System.out.println("Base64解码结果："+new String(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Base64加密
     */
    public static String encryptBASE64(byte[] bytes)
    {
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String encode = base64Encoder.encode(bytes);
        System.out.println("Base64编码结果:"+encode);
        return encode;
    }

    /**
     * MD5加密
     * 使用不同jar包生成
     * 分别为：
     * org.apache.commons.codec.digest;
     * org.springframework.util;
     * java.security;
     * @param msg
     */
    public static void encryMD5(String msg)
    {
//        方式一：
        String md5Hex = DigestUtils.md5Hex(msg);
        System.out.println("md5Hex结果:"+md5Hex);
//        方式二：
        byte[] bytes = org.springframework.util.DigestUtils.md5Digest(msg.getBytes());
        String res = HexUtils.toHexString(bytes);
        System.out.println("md5Digest结果:"+res);
        String md5 = org.springframework.util.DigestUtils.md5DigestAsHex(msg.getBytes());
        System.out.println("md5DigestAsHex结果:"+res);
//        方式三：
        try {
            MessageDigest m=MessageDigest.getInstance("MD5");
            m.update(msg.getBytes(CharsetUtil.UTF_8));
            byte s[ ]=m.digest( );
            String result="";
            for (int i=0; i<s.length;i++){
                result+=Integer.toHexString((0x000000ff & s[i]) | 0xffffff00).substring(6);
            }
            System.out.println("MessageDigest结果:"+result);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * DES加密-对称加密
     * 1、要求密钥必须是8个字节，即64bit长度
     * 2、因为密钥是byte[8] , 代表字符串也可以是非可见的字节,可以与Base64编码算法一起使用
     * 3、加密、解密都需要通过字节数组作为数据和密钥进行处理
     * 字符串转字节或字节转字符串时 一定要加上编码，否则可能出现乱码
     * @param mode 模式： 加密（Cipher.ENCRYPT_MODE），解密（Cipher.DECRYPT_MODE）
     * @param data 代加密数据 为8个字节64位
     * @param secretKey 密钥 为8个字节64位
     * @return
     */
    public static byte[] desEncrypt(int mode,byte[] data, String secretKey) {
        try{
            //Cipher对象实际完成加密操作
//            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
//            //用密匙初始化Cipher对象
//            cipher.init(mode, generateKey(secretKey));
//            // 为了防止解密时报javax.crypto.IllegalBlockSizeException: Input length must be multiple of 8 when decrypting with padded cipher异常，
//            // 不能把加密后的字节数组直接转换成字符串
//            byte[] buf = cipher.doFinal(data);
//            return new String(Base64Utils.encode(buf));
            DESKeySpec keySpec=new DESKeySpec(secretKey.getBytes());
            SecretKeyFactory keyFactory=SecretKeyFactory.getInstance("DES");
            SecretKey key=keyFactory.generateSecret(keySpec);

            Cipher cipher=Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(mode, key, new IvParameterSpec(keySpec.getKey()));
            byte[] buf = cipher.doFinal(data);
            return buf;
        }catch(Throwable e){
            e.printStackTrace();
        }
        return null;
    }

    public static String DES_CBC_Decrypt(byte[] content, byte[] keyBytes){
        try {
            DESKeySpec keySpec=new DESKeySpec(keyBytes);
            SecretKeyFactory keyFactory=SecretKeyFactory.getInstance("DES");
            SecretKey key=keyFactory.generateSecret(keySpec);

            Cipher cipher=Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(keyBytes));
            byte[] result=cipher.doFinal(content);
//            String decode = Base64.encode(result);
//            return decode;
            return new String(result,CharsetUtil.UTF_8);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("exception:"+e.toString());
        }
        return null;

    }

    /**
     * 获得秘密密钥
     * KeyGenerator， KeyPairGenerator，KeyFactory，SecretKeyFactory这四个类区别:
     * KeyGenerator和SecretKeyFactory，都是javax.crypto包的，生成的key主要是提供给AES，DES，3DES，MD5，SHA1等 对称 和 单向 加密算法。
     * KeyPairGenerator和KeyFactory，都是java.security包的，生成的key主要是提供给DSA，RSA， EC等 非对称加密算法。
     * 还有一个同时实现了Key和KeySpec接口的SecretKeySpec类，该类可以直接用keyBytes生成原始的key，即没有加工过，不同上面的Factory和Generator。
     * @param secretKey
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static SecretKey generateKey(String secretKey) throws NoSuchAlgorithmException{
        // DES算法要求有一个可信任的随机数源
//        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
//        secureRandom.setSeed(secretKey.getBytes());
//        // 为我们选择的DES算法生成一个KeyGenerator对象
//        KeyGenerator kg = null;
//        try {
//            kg = KeyGenerator.getInstance("DES");
//        } catch (NoSuchAlgorithmException e) {
//        }
//        kg.init(secureRandom);
//        //kg.init(56, secureRandom);
//        // 生成密钥
//        return kg.generateKey();
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            DESKeySpec keySpec = new DESKeySpec(secretKey.getBytes(CharsetUtil.UTF_8));
            keyFactory.generateSecret(keySpec);
            return keyFactory.generateSecret(keySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }







}
