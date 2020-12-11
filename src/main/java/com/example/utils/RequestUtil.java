package com.example.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.deploy.security.X509DeployTrustManager;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @Author: HYX
 * @Date: 2020/12/9 14:54
 */
public class RequestUtil {

    private static final String CHARSET_NAME = "UTF-8";

    /**
     * HTTP请求
     * @param requestUrl 请求地址
     * @param requestMethod 请求类型
     * @param requestParam 请求参数
     * @return 响应结果
     */
    public static String httpRequest(String requestUrl,String requestMethod,String requestParam,boolean ignoreSsl)
    {
        StringBuffer responseBuf = null;
        try {
            if (ignoreSsl)
            {
                SslUtil.ignoreSsl();
            }
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod(requestMethod);
            conn.connect();
            //往服务器端写入内容，发送请求参数
            if (null != requestParam)
            {
                OutputStream out = conn.getOutputStream();
                out.write(requestParam.getBytes(CHARSET_NAME));
                out.close();
            }
            //接收服务端返回信息
            InputStream is = conn.getInputStream();
            InputStreamReader isr = new InputStreamReader(is,CHARSET_NAME);
            BufferedReader br = new BufferedReader(isr);
            responseBuf = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null)
            {
                responseBuf.append(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return responseBuf.toString();
    }

    /**
     * HTTPS请求
     * @param requestUrl 请求地址
     * @param requestMethod 请求类型
     * @param requestParam 请求参数
     * @return 响应结果
     */
    public static String httpsRequest(String requestUrl,String requestMethod,String requestParam)
    {
        StringBuffer responseBuf = null;
        try {
            //创建SSLContext对象 并使用我们指定的信任管理器初始化
            SSLContext sslContext = SSLContext.getInstance("SSL");
            TrustManager[] trustManagers = {new X509TrustManagerUtil()};
            sslContext.init(null,trustManagers,new SecureRandom());
            //获取SSLSocketFactory对象
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(requestMethod);
            //设置当前实例使用的SSLSocketFactory
            conn.setSSLSocketFactory(sslSocketFactory);
            conn.connect();
            //往服务器端写入内容，发送请求参数
            if (null != requestParam)
            {
                OutputStream out = conn.getOutputStream();
                out.write(requestParam.getBytes(CHARSET_NAME));
                out.close();
            }
            //接收服务端返回信息
            InputStream is = conn.getInputStream();
            InputStreamReader isr = new InputStreamReader(is,CHARSET_NAME);
            BufferedReader br = new BufferedReader(isr);
            responseBuf = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null)
            {
                responseBuf.append(line);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseBuf.toString();
    }

    public static void main(String[] args) {
        String url = "https://localhost:8080/user/getUserList";
        String method = "GET";
        String httpRequest = httpsRequest(url, method, null);
        System.out.println(httpRequest);
    }




}
