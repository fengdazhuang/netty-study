package com.fzz.IO.normalIO;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * 多个ByteBuffer可以按顺序写入同一个channel
 *
 */
public class Test4 {
    public static void main(String[] args) {
        try {
            ByteBuffer buffer1 = StandardCharsets.UTF_8.encode("abc");
            ByteBuffer buffer2 = StandardCharsets.UTF_8.encode("123");
            ByteBuffer buffer3 = StandardCharsets.UTF_8.encode("fzz");

            FileChannel channel = new RandomAccessFile("union.txt", "rw").getChannel();
            channel.write(new ByteBuffer[]{buffer1,buffer2,buffer3});

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
