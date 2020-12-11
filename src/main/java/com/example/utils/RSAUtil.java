package com.example.utils;

import io.netty.util.CharsetUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * RSA（非对称加密）
 * RSA加密对明文的长度有所限制，规定需加密的明文最大长度=密钥长度-11（单位是字节，即byte），
 * 所以在加密和解密的过程中需要分块进行。而密钥默认是1024位，即1024位/8位-11=128-11=117字节。所以默认加密前的明文最大长度117字节，解密密文最大长度为128字。
 * 那么为啥两者相差11字节呢？是因为RSA加密使用到了填充模式（padding），即内容不足117字节时会自动填满，用到填充模式自然会占用一定的字节，而且这部分字节也是参与加密的。
 * 那密钥长度的设置就是下面这段代码
 * generator.initialize(1024);
 * 大家可根据自己的需求自行调整，当然非对称加密随着密钥变长，安全性上升的同时性能也会有所下降。
 * @Author: HYX
 * @Date: 2020/12/8 15:24
 */
public class RSAUtil {

    /**
     * 密钥长度
     */
    private static final int KEY_LENGTH = 1024;
    /**
     * 加密算法RSA
     */
    private static final String KEY_ALGORITHM = "RSA";
    /**
     * 签名算法
     */
    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;


    /**
     * 获取密钥对
     * @return
     */
    public static KeyPair genKeyPair()
    {
        try {
            // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            // 初始化密钥对生成器，密钥大小为96-1024位
            SecureRandom secureRandom = new SecureRandom();
            // 生成一个密钥对，保存在keyPair中
            keyPairGen.initialize(KEY_LENGTH,new SecureRandom());
            KeyPair keyPair = keyPairGen.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();
            String priKey = Base64.getEncoder().encodeToString(privateKey.getEncoded());
            String pubKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            System.out.println("公钥："+pubKey);
            System.out.println("私钥："+priKey);
            return keyPair;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 公钥加密
     * 支持分块加密
     * @param content 待加密内容
     * @param publicKey 公钥
     * @return 加密结果
     */
    public static String encryptByPublicKey(String content,String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] decodeKey = Base64.getDecoder().decode(publicKey);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(decodeKey));
        //RSA加密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE,pubKey);
        //分块加密
        int length = content.getBytes(CharsetUtil.UTF_8).length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        while (length - offset > 0)
        {
            if (length - offset > MAX_ENCRYPT_BLOCK)
            {
                cache = cipher.doFinal(content.getBytes(CharsetUtil.UTF_8),offset,MAX_ENCRYPT_BLOCK);
            }else
            {
                cache = cipher.doFinal(content.getBytes(CharsetUtil.UTF_8),offset,length - offset);
            }
            out.write(cache,0,cache.length);
            i++;
            offset = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    /**
     * 私钥加密
     * @param content 待加密内容
     * @param privateKey 私钥
     * @return 加密结果
     */
    public static String encryptByPrivateKey(String content,String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] decodeKey = Base64.getDecoder().decode(privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey priKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decodeKey));
        //RSA加密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE,priKey);
        byte[] doFinal = cipher.doFinal(content.getBytes(CharsetUtil.UTF_8));
        return Base64.getEncoder().encodeToString(doFinal);
    }

    /**
     * 公钥解密
     * @param content 待解密字符串
     * @param publicKey 公钥
     * @return 解密结果
     */
    public static String decryptByPublicKey(String content,String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] decodeKey = Base64.getDecoder().decode(publicKey);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(decodeKey));
        //RSA解密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE,pubKey);
        byte[] decode = Base64.getDecoder().decode(content.getBytes(CharsetUtil.UTF_8));
        byte[] doFinal = cipher.doFinal(decode);
        return new String(doFinal);
    }

    /**
     * 私钥解密
     * 支持分块解密
     * @param content 待解密字符串
     * @param privateKey 私钥
     * @return 解密结果
     */
    public static String decryptByPrivateKey(String content,String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] decodeKey = Base64.getDecoder().decode(privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey priKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decodeKey));
        //RSA解密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE,priKey);
        byte[] decode = Base64.getDecoder().decode(content.getBytes(CharsetUtil.UTF_8));
        //分块解密
        int length = decode.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        while (length - offset > 0)
        {
            if (length - offset > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(decode, offset, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(decode, offset, length - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_DECRYPT_BLOCK;
        }
        byte[] byteArray = out.toByteArray();
        return new String(byteArray);
    }

    /**
     * 私钥签名
     * @param content 待签名内容
     * @param privateKey 私钥
     * @return 签名结果
     */
    public static String sign(String content,String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        byte[] keyBytes = Base64.getDecoder().decode(privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey priKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(keyBytes));
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(Base64.getEncoder().encode(content.getBytes(CharsetUtil.UTF_8)));
        byte[] bytes = signature.sign();
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * 公钥验签
     * @param content 签名原文
     * @param publicKey 公钥
     * @param sign 签名结果
     * @return
     */
    public static boolean verify(String content,String publicKey,String sign) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        byte[] decode = Base64.getDecoder().decode(publicKey);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(decode));
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(Base64.getEncoder().encode(content.getBytes(CharsetUtil.UTF_8)));
        return signature.verify(Base64.getDecoder().decode(sign));
    }



    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidKeySpecException, SignatureException {
//        genKeyPair();
        String content = "IOT评审会，确认方案是否可行";
        String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCoIDgszky583uQyEnyy7zBdIx1lycRHU3Ft0otJyn5Rbf7RpGuBdZ+eR4x4jEn6wdmsXAOHD5p2deHJXV2JAOSlJJqqgeTgUwAr3CIZYruMFHX0x+blj+leONHNFutn2KRz5Ldy0zLWCjNwZeJYwzEa/MIKRVM0JwlVX2RLrExSwIDAQAB";
        String privateKey="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKggOCzOTLnze5DISfLLvMF0jHWXJxEdTcW3Si0nKflFt/tGka4F1n55HjHiMSfrB2axcA4cPmnZ14cldXYkA5KUkmqqB5OBTACvcIhliu4wUdfTH5uWP6V440c0W62fYpHPkt3LTMtYKM3Bl4ljDMRr8wgpFUzQnCVVfZEusTFLAgMBAAECgYByixD6pcrsXZBwAaUv/9Kn0qnna4W4SUi1Tb0Bqk8Uf4VrcpbKAzwxin8h6UMoOR4fvv/i/ScgosQOVV0DkmHDnMn8LE1amkfxm4H9IsH0ig8v8GI7bkfaTaIoc2q5sxar9Mksxxx/DgjnLHgFnYGxfPRE2n+ksmq9ShE/DfBBMQJBAOyHqUFQZ+oSuGwsXZiz40Xy5Zn0P17qcGBdIy20Pbp8vIEAJTl/6fiOaiJmxQwfM0+aELVPLaQqlROXfYKSIoUCQQC19xh532/j2rcAnHKaHYi7IOjwAlgM+lzdvd6kTn4u24qXXtLxIR2hGkX13MhMYeHM0GVqgXlB+1c9MbfNvxWPAkBxRZJ3teLdxPNO0nxMObGYePcdoEuMz+bbLx8+rvd0zHGVsZUiETDiGImZSfAJmxN+hoCD45Qu7zmPNVgk5IjlAkBR1SMDxBFMtlKwiqsbVgbkqQM41MUGaR2Ud0wGNmqW5hKoynMxD8SEbA1kaXsGpspmUp4ZTMrceo0cLxzLAZFPAkEA0+tZrG1+9NCXqSSSRiAkQW4HG7gY8QL1HUTLqZwuoUB/M6KcwHYtDlkzE4VqM34NW4XVDp1B9atsE5IUl5ozzQ==";
        String encrypt = encryptByPublicKey(content, publicKey);
        System.out.println("公钥加密："+encrypt);
        String decrypt = decryptByPrivateKey(encrypt, privateKey);
        System.out.println("私钥解密："+decrypt);
        String encrypt1 = encryptByPrivateKey(content, privateKey);
        System.out.println("私钥加密："+encrypt1);
        String decrypt1 = decryptByPublicKey(encrypt1, publicKey);
        System.out.println("公钥解密："+decrypt1);
        String sign = sign(content, privateKey);
        System.out.println("私钥签名："+sign);
        boolean verify = verify(content, publicKey, sign);
        System.out.println("公钥验签："+verify);
    }
}
