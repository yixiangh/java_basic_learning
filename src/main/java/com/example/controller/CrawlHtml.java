package com.example.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 爬取网页内容
 */
public class CrawlHtml {

    public static String httpRequest(String requestUrl) {
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("GET");

            //获取输入流
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);

            //读取输入流中内容
            StringBuffer stringBuffer = new StringBuffer();
            String res = null;
            while ((res = bufferedReader.readLine()) != null) {
                stringBuffer.append(res);
            }

            //释放资源
            inputStream.close();
            inputStreamReader.close();
            bufferedReader.close();
            httpURLConnection.disconnect();

            return stringBuffer.toString();
        } catch (MalformedURLException e) {
            System.out.println("地址请求出错！");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("连接出错！");
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String html = httpRequest("https://mp.weixin.qq.com/s/q_vQzvZeBHGDIeMD5onEXA");
        System.out.println(html);
    }
}
