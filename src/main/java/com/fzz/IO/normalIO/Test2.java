package com.fzz.IO.normalIO;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 向ByteBuffer中输入数据的两种方法：
 * 1 channel的read方法，根据设置的容量分批读取文件的数据
 * 2 buffer的put方法（字节数组）
 *
 * 输出ByteBuffer的数据的两种方法“
 * 1 channel的write方法，写入文件
 * 2 buffer的get方法
 */

public class Test2 {
    public static void main(String[] args) {
        try {
            FileChannel channel = new RandomAccessFile("data2.txt", "rw").getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(20);
            buffer.put("abcdefg".getBytes());
            buffer.flip();
//            while(buffer.hasRemaining()){
//                System.out.print( (char) buffer.get());
//            }
            channel.write(buffer);
            buffer.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public void encodeAndDecode(){
        ByteBuffer buffer1 = StandardCharsets.UTF_8.encode("helloWorld");
        ByteBuffer buffer2 = Charset.defaultCharset().encode("你好");
        String toString = StandardCharsets.UTF_8.decode(buffer1).toString();
        System.out.println(toString);
    }
}
