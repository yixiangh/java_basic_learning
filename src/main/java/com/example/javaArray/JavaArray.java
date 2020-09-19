package com.example.javaArray;

import java.io.File;
import java.io.IOException;

/**
 * 数组
 */
public class JavaArray {

    /**
     * 一维数组
     */
    public static void oneArray() {
        int[] s;
        s = new int[5];
        for (int i = 0; i < s.length; i++) {
            s[i] = i;
        }

        int z[] = new int[5];//数组的复制
        System.arraycopy(s, 0, z, 0, s.length);
        z[3] = 100;
        System.out.println(s[3]);
    }

    /**
     * 二维数组
     */
    public static void twoArray() {
//        int aa[][] = new int[5][];//三维数组就是[][][] 四维就是[][][][]以此类推
//        aa[0] = new int[2];
//        aa[1] = new int[4];
        int aa[][] = {{1, 2}, {3, 4, 5, 6}, {7, 8, 9, 10}, {11, 12}};
        for (int i = 0; i < aa.length; i++) {
            for (int j = 0; j < aa[i].length; j++) {
                System.out.println(aa[i][j]);
            }
        }
    }

    public static void main(String[] args) throws IOException {
//        oneArray();
//        twoArray();
//            String aa = "张三";
//            String bb = "张三";
//            char[] cc = aa.toCharArray();
//        for (int i = 0; i < cc.length; i++) {
//            System.out.println(cc[i]);
//        }
//        System.out.println(aa.equals(bb));
    }

}
