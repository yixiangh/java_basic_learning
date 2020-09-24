package com.example.javaFile.file;

import java.io.*;

/**
 * java流分类：
 * 字节流：inputStream/outputStream
 * 文件节点流：FileInputStream/FileOutputStream
 * 内存节点流：ByteArrayInputStream/ByteArrayOutputStream
 *
 * 字符流：Reader/Writer
 * 文件节点流：FileReader/FileWriter
 * 内存节点流：StringWriter/CharArrayWriter
 *
 * 处理流：
 * 处理流相对于节点流来说，节点流都是直接使用操作系统底层方法读取硬盘中的数据，缓冲流是处理流的一种实现，增强了节点流的性能，
 * 为了提高效率，缓冲流类在初始化对象的时候，内部有一个缓冲数组，一次性从底层流中读取数据到数组中，
 * 程序中执行read()或者read(byte[])的时候，就直接从内存数组中读取数据。
 * 有以下处理流：
 * 缓冲流：
 * 字节缓冲流：BufferedInputStream/BufferedOutputStream
 * 字符缓冲流：BufferedReader/BufferedWriter
 * 转换流：
 * InputStreamReader：Reader的子类，读取字节，并使用指定的字符集将其解码为字符。字符集可以自己指定，也可以使用平台的默认字符集。
 * OutputStreamWriter：Writer的子类，使用指定的字符集将字符编码为字节。字符集可以自己指定，也可以使用平台的默认字符集。
 * 对象流：
 * jdk提供了对象序列化的方式，该序列化机制将对象转为二进制流，二进制流主要包括对象的数据、对象的类型、对象的属性。可以将java对象转为二进制流写入文件中。文件会持久保存了对象的信息。
 * ObjectOutputStream：
 * ObjectInputStream：
 * 管道流：
 * 管道流主要用于两个线程间的通信，即一个线程通过管道流给另一个线程发数据
 * 注意：线程的通信一般使用wait()/notify(),使用流也可以达到通信的效果，并且可以传递数据
 * 使用的类是如下
 * PipedInputStream和PipedOutStream
 * PipedReader和PipedWriter
 * 数据流：
 * 主要方便读取Java基本类型以及String的数据,有DataInputStream 和 DataOutputStream两个实现类
 *
 * flush()：强制刷新缓冲区的数据到目的地,刷新后流对象还可以继续使用
 * close(): 强制刷新缓冲区后关闭资源，关闭后流对象不可以继续使用
 * 缓冲区：可以理解为内存区域，程序频繁操作资源（如文件）时，性能较低，因为读写内存较快，利用内存缓冲一部分数据，不要频繁的访问系统资源，是提高效率的一种方式
 * 具体的流只要内部有维护了缓冲区，必须要close()或者flush()，不然不会真正的输出到文件中
 */
public class JavaFile {
    public static void main(String[] args) {
        String url = "E:\\mkdir1" + File.separator + "mkdir2";
        String fileName = "test.txt";
        String filePath = url+File.separatorChar+fileName;
        System.out.println("filePath:"+filePath);
//        createFile(url,fileName);
//        writeFile("你好，my name is meimeili",filePath);
//        objectWrite(filePath);
        objectRead(filePath);
    }


    /**
     * 对象流-读取
     * 反序列化
     */
    public static void objectRead(String filePath)
    {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(new File(filePath));
            ois = new ObjectInputStream(fis);

            CarEntity car = (CarEntity) ois.readObject();
            System.out.println("对象读取成功，内容："+car.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null)
                {
                    fis.close();
                }
                if (ois != null)
                {
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 对象流-写出
     * 对象序列化写出
     */
    public static void objectWrite(String filePath)
    {
        CarEntity car = new CarEntity();
        car.setName("奥迪");
        car.setColor("耀黑");
        car.setWheel(4);

        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            File file = new File(filePath);
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);

            oos.writeObject(car);
            oos.flush();
            System.out.println("对象写出成功！");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (fos != null)
                {
                    fos.close();
                }
                if (oos != null)
                {
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 写入数据到文件
     */
    public static void writeFile(String msg,String filePath)
    {
        File file = new File(filePath);
        FileOutputStream fos = null;
        try {
            //true表示追加，false表示每次写入前会先清空再写入
            boolean append = true;
            fos = new FileOutputStream(file,append);
            byte[] msgByte = msg.getBytes();
            FileDescriptor fd = fos.getFD();
            fos.write(msgByte);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fos != null)
            {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 判断文件是否存在，如果不存在就新建
     * @param url
     * @param fileName
     */
    public static void createFile(String url,String fileName)
    {
        File file = new File(url, fileName);
        file.getParentFile().mkdirs();
        boolean boo = file.exists();
        if (!boo)
        {
            //创建文件
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(boo);
    }
}
