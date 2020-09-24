package com.example.javaFile.file;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * 图片压缩
 * Thumbnailator是一个用来生成图像缩略图的 Java类库，通过很简单的代码即可生成图片缩略图，也可直接对一整个目录的图片生成缩略图。
 * @Author: HYX
 * @Date: 2020/9/15 14:14
 */
public class ImageUtil {

    private static int mb = 1048576;// 1MB

    public static void main(String[] args) throws IOException {
        imageCompress();
    }

    /**
     * 图片压缩
     * @throws IOException
     */
    public static void imageCompress() throws IOException {
        String uploadPath = "E:\\";
        File f = new File("E:\\sfzimg.jpg");
        System.out.println("原图大小："+f.length()/mb);
        if (!f.exists()){
            f.mkdir();
        }
        FileInputStream in_file = new FileInputStream(f);

        // 转 MultipartFile
        MultipartFile file = new MockMultipartFile("ysimg",in_file);
        String random = UUID.randomUUID().toString();
        try {
            Thumbnails.of(file.getInputStream()).scale(0.5f).outputFormat("jpg").outputQuality(0.5).toFile(uploadPath + random);
        } catch (IOException e) {
            System.out.println("上传图片失败!");
            e.printStackTrace();
        }
        System.out.println(random+".jpg");
    }

}
