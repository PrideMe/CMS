package com.util.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by 22717 on 2017/12/29.
 * 01011101这8位(bit)代表了一个字节Byte。即1Byte = 1B = 8bit
 * 学习IO流，字节流
 * InputStream的read方法，首先在开始读之前，指针的位置是在开始字节之前
 * 运行一遍此方法后指针移动到第一个字节后，第二个字节之前
 * 方法：
 * read() 返回一个字节
 * read(byte[] b) 返回读入字节数组的字节总数
 * read(byte[] b,int off,int len)尝试读取len个字节放入数组
 * available() 返回实际可读入流的字节长度【不适用于网络获取】
 */
public class IOTest {
    public static void main(String[] args) {
        //输入流对象声明
        InputStream inputStream = null;
        OutputStream outputStream = null;
        int oneByte;
        byte[] buffer = new byte[6];
        try {
            //构建对于此文件的输入流对象
            inputStream = new FileInputStream(new File("E:\\hadoop教程\\细细品问Hadoop集群\\about云分享说明.txt"));
            outputStream = new FileOutputStream(new File("E:\\hadoop教程\\细细品问Hadoop集群\\about云分享说明.txt"));

            String s = "移动字节的长度";
            outputStream.write(buffer,0,s.getBytes().length);

//            int a = inputStream.available();
//            while((oneByte=inputStream.read(buffer, 0,a))!=-1){
//                System.out.println(new String(buffer,"utf-8"));
//            }
//            //移动指针，从开始字节之前移动一个字节的长度
//            int oneByte = inputStream.read();
//            System.out.println(oneByte);
//            //继续移动指针，从第二个字节开始之前的位置向下移动，获取一个字节的长度
//            oneByte = inputStream.read();
//            System.out.println(oneByte);
//
//            oneByte = inputStream.read();
//            System.out.println(oneByte);
//            //移动到最后字节的尾部，下一个字节读取为空，返回-1
//            oneByte = inputStream.read();
//            System.out.println(oneByte);

            //读取3个字节从buffer的0位置开始放入buffer
//            inputStream.read(buffer,0,3);
//            char c;
//            for (byte b : buffer) {
//                if (b<0)
//                    c= '%';
//                else
//                    c= (char) b;
//                System.out.print(c);
//            }
//            while ((oneByte = inputStream.read(buffer)) != -1){
//                System.out.print(oneByte+" ");
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            System.out.println("结束");
            try {
                assert inputStream != null;
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
