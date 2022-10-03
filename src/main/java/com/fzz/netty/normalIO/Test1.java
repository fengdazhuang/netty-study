package com.fzz.netty.normalIO;

import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


/**
 *
 * ByteBuffer 正确使用姿势
 * 1. 向 buffer 写入数据，例如调用 channel.read(buffer)
 * 2. 调用 flip() 切换至**读模式**
 * 3. 从 buffer 读取数据，例如调用 buffer.get()
 * 4. 调用 clear() 或 compact() 切换至**写模式**
 * 5. 重复 1~4 步骤
 *
 *
 * FileInputStream 和 FileOutputStream为单向的，只能读和写
 * RandomAccessFile既可以读也可以写
 *
*/
public class Test1 {
    public static void main(String[] args) {
        try {
            RandomAccessFile file = new RandomAccessFile("data1.txt", "rw");

            //FileInputStream file = new FileInputStream("data1.txt");
            FileChannel channel = file.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(20);
            int len=-1;
            while((len=channel.read(buffer))!=-1){
                buffer.flip();
                while(buffer.hasRemaining()){
                    System.out.print((char)buffer.get());
                }
                buffer.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
