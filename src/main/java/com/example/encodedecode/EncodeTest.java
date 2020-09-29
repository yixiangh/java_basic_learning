package com.example.encodedecode;

import com.example.javaFile.file.CarEntity;
import io.netty.util.CharsetUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * java-编解码
 * @Author: HYX
 * @Date: 2020/7/23 15:15
 */
public class EncodeTest {

    public static void main(String[] args) throws IOException {
//        encodeEntityTest();
        encodeUrlTest();
//        encodeFileTest();
    }

    public static void encodeUrlTest()
    {
        String url = "/myTocat/bin/file/qq_asfdajsk.jpg";
        String encodeToString = Base64.getEncoder().encodeToString(url.getBytes(CharsetUtil.UTF_8));
        System.out.println("Base64-Encoder编码结果："+encodeToString);
        byte[] decode = Base64.getDecoder().decode(encodeToString);
        System.out.println("Base64-Encoder解码结果:"+new String(decode,CharsetUtil.UTF_8));
        String urlEncode = Base64.getUrlEncoder().encodeToString(url.getBytes(CharsetUtil.UTF_8));
        System.out.println("Base64-UrlEncode编码结果:"+urlEncode);
        byte[] urlDecode = Base64.getUrlDecoder().decode(urlEncode);
        System.out.println("Base64-UrlEncode解码结果:"+new String(urlDecode,CharsetUtil.UTF_8));
        String mimeEncode = Base64.getMimeEncoder().encodeToString(url.getBytes(CharsetUtil.UTF_8));
        System.out.println("Base64-mimeEncode编码结果:"+mimeEncode);
        byte[] mimeDecode = Base64.getMimeDecoder().decode(mimeEncode);
        System.out.println("Base64-mimeDecode解码结果:"+new String(mimeDecode,CharsetUtil.UTF_8));
    }

    public static void encodeEntityTest()
    {
        CarEntity car = new CarEntity();
        car.setName("奥迪");
        car.setCount("测试");
        car.setWheel(4);
        car.setColor("曜石黑色");
        String encodeToString = Base64.getEncoder().encodeToString(car.toString().getBytes(CharsetUtil.UTF_8));
        System.out.println("Base64-Encoder编码结果："+encodeToString);
        byte[] decode = Base64.getDecoder().decode(encodeToString);
        System.out.println("Base64-Encoder解码结果:"+new String(decode,CharsetUtil.UTF_8));
        String urlEncode = Base64.getUrlEncoder().encodeToString(car.toString().getBytes(CharsetUtil.UTF_8));
        System.out.println("Base64-UrlEncode编码结果:"+urlEncode);
        byte[] urlDecode = Base64.getUrlDecoder().decode(urlEncode);
        System.out.println("Base64-UrlEncode解码结果:"+new String(urlDecode,CharsetUtil.UTF_8));
        String mimeEncode = Base64.getMimeEncoder().encodeToString(car.toString().getBytes(CharsetUtil.UTF_8));
        System.out.println("Base64-mimeEncode编码结果:"+mimeEncode);
        byte[] mimeDecode = Base64.getMimeDecoder().decode(mimeEncode);
        System.out.println("Base64-mimeDecode解码结果:"+new String(mimeDecode,CharsetUtil.UTF_8));
    }

    public static void encodeFileTest() throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get("C:\\Users\\MI\\Pictures\\Saved Pictures\\f35001.jpg"));
        String encodeToString = Base64.getEncoder().encodeToString(bytes);
        System.out.println("Base64-Encoder编码结果："+encodeToString);
        byte[] decode = Base64.getDecoder().decode(encodeToString);
        File file = new File("E:\\test\\1234.jpg");
        if (!file.exists())
        {
            file.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(decode);
    }



}
