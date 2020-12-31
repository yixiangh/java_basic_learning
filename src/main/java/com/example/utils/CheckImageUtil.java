package com.example.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 图片格式大小校验
 * @Author: HYX
 * @Date: 2020/12/16 10:28
 */
public class CheckImageUtil {

    /**
     * 图片像素判断（宽高）
     * @param multipartFile 待Check图片
     * @param imgWidth 指定宽度
     * @param imgHeight 指定高度
     * @return 图片宽高与指定宽高相等时返回true
     * @throws IOException
     */
    public static boolean checkImgElement(MultipartFile multipartFile, int imgWidth, int imgHeight) throws IOException {
        if (!multipartFile.isEmpty())
        {
            return false;
        }
        File file = fileTypeTransfer(multipartFile);
        boolean result =  doCheckImgElement(file,imgWidth,imgHeight);
        deleteFile(file);
        return result;
    }

    /**
     * 图片像素判断（宽高）
     * @param file 待Check图片
     * @param imgWidth 指定宽度
     * @param imgHeight 指定高度
     * @return 图片宽高与指定宽高相等时返回true
     * @throws IOException
     */
    public static boolean checkImgElement(File file, int imgWidth, int imgHeight) throws IOException {
        if (!file.exists())
        {
            return false;
        }
        return doCheckImgElement(file,imgWidth,imgHeight);
    }

    /**
     * 校验图片像素（宽高）
     * @param file 待Check图片
     *  @param imgWidth 指定宽度
     *  @param imgHeight 指定高度
     *  @return 图片宽高与指定宽高相等时返回true
     */
    private static boolean doCheckImgElement(File file, int imgWidth, int imgHeight) throws IOException {
        Boolean result = false;
        BufferedImage bufferedImage = ImageIO.read(file);
        if (bufferedImage != null)
        {
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            if (imgHeight == height && imgWidth == width)
            {
                result = true;
            }
        }
        return result;
    }

    /**
     * 校验图片比例
     * @param file 图片
     * @param imageWidth 指定宽
     * @param imageHeight 指定高
     * @return true：符合要求
     * @throws IOException
     */
    public static boolean checkImgScale(File file, int imageWidth, int imageHeight) throws IOException {
        Boolean result = false;
        if (!file.exists()) {
            return false;
        }
        BufferedImage bufferedImage = ImageIO.read(file);
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        if (imageHeight != 0 && height != 0) {
            int scale1 = imageHeight / imageWidth;
            int scale2 = height / width;
            if (scale1 == scale2) {
                result = true;
            }
        }
        return result;
    }

    /**
     * 校验图片的大小
     * @param file 文件
     * @param imageMaxSize 图片最大值(KB)
     * @return true：上传图片小于图片的最大值
     */
    public static boolean checkImgSize(File file, Long imageMaxSize) {
        if (!file.exists()) {
            return false;
        }
        Long size = file.length() / 1024; // 图片大小
        if (imageMaxSize == null) {
            return true;
        }
        if (size.intValue() <= imageMaxSize) {
            return true;
        }
        return false;
    }

    /**
     * MultiparFile 转换为 File
     * 文件名会被重置为UUID
     * @param multipartFile 待转换文件
     * @return 转换后的File
     * @throws IOException
     */
    public static File fileTypeTransfer(MultipartFile multipartFile) throws IOException {
        // 获取文件名
        String fileName = multipartFile.getOriginalFilename();
        // 获取文件后缀
        String prefix=fileName.substring(fileName.lastIndexOf("."));
        // 用uuid作为文件名，防止生成的临时文件重复
        final File excelFile = File.createTempFile(UUID.randomUUID().toString(), prefix);
        // MultipartFile to File
        multipartFile.transferTo(excelFile);
        return excelFile;
    }

    /**
     * 删除
     * @param files
     */
    private static void deleteFile(File... files) {
        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }
    }



    }
