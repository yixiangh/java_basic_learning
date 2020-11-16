package com.example.javaIo;

import java.io.*;

/**
 * Java之IO  流
 * 流按照方向不同分为：输入流/输出流
 * 流按照处理数据单位不同分为：字符流/字节流
 * 流按照功能不同分为：节点流/处理流
 *
 */
public class JavaIO {

    /**
     * 节点流之字节流
     * FileInputStream/FileOutputStream在读取/写入时是单个字节执行的
     */
    public static void readFileByByte() {
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        int aa = 0;
        try {
            inputStream = new FileInputStream("E:\\test.txt");
            outputStream = new FileOutputStream("E:\\test1.txt");
            while ((aa = inputStream.read()) != -1) {
                System.out.print((char) aa);
                outputStream.write(aa);
            }


        } catch (FileNotFoundException e) {
            System.out.println("文件未找到！");
            System.exit(-1);//退出程序
        } catch (IOException e) {
            System.out.println("文件读取失败！");
            System.exit(-1);//退出程序
        }

    }

    /**
     * 节点流之字符流
     * FileReader/FileWriter在执行时可一个字符一个字符读取/写入  能够解决字节流在读取/写入中文时，不能完整读取/写入问题（字节流在写入中文时，需要将中文转换成字节才可写入）
     */
    public static void readFileByCharacter() {
        FileReader reader = null;
        FileWriter writer = null;
        int aa = 0;
        try {
            reader = new FileReader("E:\\test.txt");
            writer = new FileWriter("E:\\test1.txt");
            while ((aa = reader.read()) != -1) {
                System.out.print((char) aa);
                writer.write(aa);
            }
//            reader.close();
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("文件未找到");
        } catch (IOException e) {
            System.out.println("文件读取失败");
        }

    }

    /**
     * 处理流之缓冲流BufferedIO
     * BufferedReader/BufferedWrite用于在读取或者写入时，提供缓冲区，解决节点流只能一个字节或者一个字符读取以及写入的问题
     * 在使用BufferedReader/BufferedWrite后程序可将读取/写入的内容先缓冲到缓冲区，在达到指定的大小后，再一次行读取/写入
     */
    public static void bufferedIO() {
        BufferedReader bis = null;
        BufferedWriter bw = null;
        String line = null;
        try {
            bis = new BufferedReader(new FileReader("E:\\test.txt"));
            bw = new BufferedWriter(new FileWriter("E:\\test1.txt"));
            while ((line = bis.readLine()) != null) {
                System.out.println(line);
                bw.write(line);
                bw.newLine();//换行
            }
            bw.flush();
            bw.close();
        } catch (FileNotFoundException e) {
            System.out.println("文件未找到");
        } catch (IOException e) {
            System.out.println("文件读取失败");
        }
    }

    /**
     * 转换流
     */
    public static void transformIO() {
        OutputStreamWriter osi = null;//转换为输出字符流
        InputStreamReader isr = null;//转换为输入字符流
        int line = 0;
        String readLine = null;
        try {
//            osi = new OutputStreamWriter(new FileOutputStream("E:\\test.txt",true),"iso8859_1");//FileOutputStream()第二个true表示在文件中追加，不写或false表示清空，重新填写,OutputStreamWriter（）第二个参数表示设置字符编码
//            System.out.println("默认编码："+osi.getEncoding());
//            osi.write("你好  我是   helloword");
//            osi.flush();
//            osi.close();
            isr = new InputStreamReader(new FileInputStream("E:\\test.txt"));
//            while ((line = isr.read()) != -1)
//            {
//                System.out.print((char)line);
//            }
            BufferedReader br = new BufferedReader(isr);//将InputStreamReader使用处理流BufferedReader()处理后可使用BufferedReader()的readLine()方法直接读取一行数据
            while ((readLine = br.readLine()) != null) {
                System.out.print(readLine);
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("文件未找到");
        } catch (IOException e) {
            System.out.println("文件读取失败");
        }
    }

    /**
     * 数据流:DataInputStream和DataOutputStream
     * 分别继承自InputStream和OutputStream
     * 其可用于读取/写入与机器无关的Java原始类型数据（如：int,double）
     */
    public static void dataIoAndByteArrayIo() {
        //方法解释：ByteArrayOutputStream是在在内存中分配一个字节数组的输出流，用于存储字节数据
        //然后使用DataOutputStream将如int double float等Java原始类型的数据传入到ByteArrayOutputStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        try {
            Double random = Math.random();
            System.out.println("random:" + random);
            dos.writeDouble(random);
            dos.writeBoolean(false);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            System.out.println(bais.available());
            DataInputStream dis = new DataInputStream(bais);
            System.out.println(dis.readDouble());
            System.out.println(dis.readBoolean());
            dos.close();
            dis.close();
        } catch (FileNotFoundException e) {
            System.out.println("文件未找到");
        } catch (IOException e) {
            System.out.println("文件读取失败");
        }
    }

    /**
     * 打印流：printIo
     * 分为：PrintStream(字节)和PrintWriter（字符）
     * PrintStream和和PrintWriter还提供了重载方法Pringln
     */
    public static void printIo1() {
        FileOutputStream outputStream = null;
        PrintStream pstream = null;
        try {
            outputStream = new FileOutputStream("E:\\test0311.txt");
            pstream = new PrintStream(outputStream);
            System.setOut(pstream);
            System.out.println("开始");
            for (int i = 0; i < 10; i++) {
                System.out.print(i);
            }
            outputStream.flush();
            outputStream.close();
            pstream.close();
        } catch (FileNotFoundException e) {
            System.out.println("文件未找到");
        } catch (IOException e) {
            System.out.println("文件读取失败");
        }

    }

    /**
     * 对象流-objectIo
     * 在java中可将实现了serializable接口的对象进行序列化,作用即直接对Object进行读取/写入
     */
    public static void objectIo() {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fos = new FileOutputStream("E:\\testObject.java");
            oos = new ObjectOutputStream(fos);
            oos.writeObject(user.class);
            oos.flush();
            oos.close();

            fis = new FileInputStream("E:\\testObject.java");
            ois = new ObjectInputStream(fis);
            System.out.println("总字节数：" + ois.available());
            Object object = ois.readObject();
            System.out.println(object);
        } catch (FileNotFoundException e) {
            System.out.println("文件未找到");
        } catch (IOException e) {
            System.out.println("文件读取失败");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        readFileByByte();
//        readFileByCharacter();
//        bufferedIO();
//        transformIO();
//        dataIoAndByteArrayIo();
//        printIo1();
        objectIo();

    }
}

class user implements Serializable {
    int aa = 10;
    int bb = 0;
    long cc = 10L;
    String dd = "dd";
}