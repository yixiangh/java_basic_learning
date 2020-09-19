package com.example.javaFile;

import java.io.File;

/**
 * file类，创建文件夹以及文件
 */
public class JavaFile {
    public static void main(String[] args) {
        String url = "E:\\mkdir1" + File.separator + "mkdir2";
        String fileName = "test.txt";
        File file = new File(url, fileName);
        file.getParentFile().mkdirs();
//        file.createNewFile();
        boolean boo = file.exists();
        System.out.println(boo);
    }
}
