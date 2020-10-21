package com.example.encodedecode;

import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * RSA加解密/加签验签
 * @Author: HYX
 * @Date: 2020/10/15 17:22
 */
@Slf4j
public class RSAUtil {

    /**
     * 密钥算法
     */
    private static String KEY_ALGORITHM = "RSA";

    /**
     * 签名使用的算法
     */
    private static String SIGN_ALGORITHM = "Sha1WithRSA";

    public static void main(String[] args) {
        String msg = "马上就要中秋节了";
//        genKeyPair();
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCEmKqd6M/kBbeMvT2tEGpLsHfpH3BzOOsmtOVjcejsJE1HD+XlIok8dnZD7y79kuZmpyIqocej/hWvtDqnNq88S+EszGUwuMp20NG4TXdE8JzPjjmlBa1T9wYmkd6EObVQ/j5X2J50fyWi/LsxElAlEIZHW96TlZk5F6x75D02JQIDAQAB";
        String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAISYqp3oz+QFt4y9Pa0Qakuwd+kfcHM46ya05WNx6OwkTUcP5eUiiTx2dkPvLv2S5manIiqhx6P+Fa+0Oqc2rzxL4SzMZTC4ynbQ0bhNd0TwnM+OOaUFrVP3BiaR3oQ5tVD+PlfYnnR/JaL8uzESUCUQhkdb3pOVmTkXrHvkPTYlAgMBAAECgYAsbZbleCeIKJ46ywHKZjCnkcZr1zvvb4fjHUOrEXTRAszd3abTIsJG/w5bbt4Y2srWZHPfQvvIqA8OI15vvrPyDYrAtgaKV8xGxVaploHqibMQswqw0uWnfMm5DZkIgJRFY9/zJihT7lXeZ695+37fvDFU15ZilY8zroXki4vhlQJBAMA+RYrfYAbu3lsviaXphbnWl3gyqLSYJRmFsPgkmBvoPlFIloJqmS579feDA8JP7FnT7Z90pMGvKXMVIp981vsCQQCwkkmhJq5PpJAO+p8STQtDoEppS+XtJN7Qzb4iJfHtOXVlBo3jaVTR0zpzKxPCH6Msr/vIzb9bUFisI5JIfR1fAkALhO+31ZC0eaW7Qwr1dRu9Of2Thz2SrLc0Y01qYQ74RR9O0ZGrOOX64hSoAyK7hvx7mXZYoq2oqW1LZw3PDrT3AkAUIOkR3OX8IEdn3bwqiuvgzrIM5OEWMqDP7tRg8jBtGaK/XIrqRMStksXUATIkbBTe8ETktEe/Mh6ZqQhxCQpDAkEAnMiVzLLRJfmJda5ceGi5Dss9INfCvi+2ifpvWNtxIMCBySS4UTNFCnLLymmsAx2C0B7J1nIeIUx70GByVUKcGA==";
//        String content = "RSA加密测试";
//        String res = RSAUtil.publicEncrypt(content.getBytes(),RSAUtil.decodePublicKey(publicKey));
//        RSAUtil.privateDecrypt(Base64.getDecoder().decode(res),RSAUtil.decodePrivateKey(privateKey));
        String sign = RSAUtil.signed(privateKey,msg);
        RSAUtil.doCheck(publicKey,sign,msg);
    }
    /**
     * 生成密钥对-RSA
     * @return
     */
    public static void genKeyPair()
    {
        //KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            //初始化密钥对生成器，密钥大小为96-1024位
            keyPairGen.initialize(1024,new SecureRandom());
            //生成密钥对
            KeyPair keyPair = keyPairGen.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();
            log.info("算法："+privateKey.getAlgorithm());
            log.info("格式："+publicKey.getFormat());
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
            RSAPublicKey rasPublicKey = (RSAPublicKey) keyPair.getPublic();
            log.info("RSA私钥："+rsaPrivateKey.getModulus());
            log.info("RSA公钥："+rasPublicKey.getPublicExponent());
            String privateKeyStr = Base64.getEncoder().encodeToString(rsaPrivateKey.getEncoded());
            String publicKeyStr = Base64.getEncoder().encodeToString(rasPublicKey.getEncoded());
            log.info("私钥字符串："+privateKeyStr);
            log.info("公钥字符串："+publicKeyStr);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将Base64编码后的公钥转换成PublicKey对象
     * @param publicKey 公钥字符串
     * @return
     */
    public static PublicKey decodePublicKey(String publicKey){
        byte[] keyBytes = Base64.getDecoder().decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey key = keyFactory.generatePublic(keySpec);
            return key;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Base64编码后的私钥转为PrivateKey对象
     * @param privateKey 私钥字符串
     * @return
     */
    public static PrivateKey decodePrivateKey(String privateKey)
    {
        byte[] decode = Base64.getDecoder().decode(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decode);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey key = keyFactory.generatePrivate(keySpec);
            return key;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 公钥加密
     * @param content 待加密数据
     * @param publicKey 公钥
     */
    public static String publicEncrypt(byte[] content,PublicKey publicKey)
    {
        try {
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE,publicKey);
            byte[] bytes = cipher.doFinal(content);
            String res = Base64.getEncoder().encodeToString(bytes);
            log.info("公钥加密结果："+res);
            return res;
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

    /**
     * 私钥解密
     * @param content 待解密数据
     * @param privateKey 私钥
     */
    public static void privateDecrypt(byte[] content,PrivateKey privateKey)
    {
        try {
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE,privateKey);
            byte[] bytes = cipher.doFinal(content);
            String res = new String(bytes,CharsetUtil.UTF_8);
            log.info("私钥解密结果："+res);
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
    }

    /**
     * 使用私钥签名
     * @param privateKey 私钥
     * @param data 待签名数据
     * @return 签名结果
     */
    public static String signed(String privateKey,String data)
    {
        PrivateKey priKey = decodePrivateKey(privateKey);
        try {
            Signature signature = Signature.getInstance(SIGN_ALGORITHM);
            signature.initSign(priKey);
            signature.update(data.getBytes(CharsetUtil.UTF_8));
            byte[] sign = signature.sign();
            String res = Base64.getEncoder().encodeToString(sign);
            log.info("私钥签名结果："+res);
            return res;
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用公钥验签
     * @param publicKey 公钥
     * @param sign 签名
     * @param data 待签名数据
     */
    public static void doCheck(String publicKey,String sign,String data)
    {
        PublicKey pubKey = decodePublicKey(publicKey);
        try {
            Signature signature = Signature.getInstance(SIGN_ALGORITHM);
            signature.initVerify(pubKey);
            signature.update(data.getBytes(CharsetUtil.UTF_8));
            boolean verify = signature.verify(Base64.getDecoder().decode(sign));
            log.info("公钥验签结果："+verify);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }

    }
}
