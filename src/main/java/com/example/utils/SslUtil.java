package com.example.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * 有些情况下，虽然服务器端使用的是https协议，但是其证书不是由权威机构颁发的，客户端如果使用jdk默认的证书会校验失败。
 * 为了在项目初期进行调试，我们可以忽略服务器证书校验。由前一篇文章可知，要达到不校验服务器证书的目的，
 * 必须将hostname校验和CA证书校验同时关闭。
 * @Author: HYX
 * @Date: 2020/12/10 17:53
 */
public class SslUtil {
    private static final Logger LOGGER = LogManager.getLogger(X509TrustManagerUtil.class);

    public static void ignoreSsl() throws NoSuchAlgorithmException, KeyManagementException {
        // 自定义证书校验器
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManagerUtil()};
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        // 自定义hostname校验器
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                LOGGER.warn("信任证书URL Host: {}:{}", hostname, session.getPeerHost());
                return true;
            }
        };
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }
}
