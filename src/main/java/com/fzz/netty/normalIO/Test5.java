package com.fzz.netty.normalIO;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 文件夹的遍历
 *
 */
public class Test5 {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("D:\\jdk\\IntelliJ IDEA 2021.2.3");
        AtomicInteger fileCount=new AtomicInteger();
        AtomicInteger dirCount = new AtomicInteger();
        AtomicInteger jarCount=new AtomicInteger();
        Files.walkFileTree(path,new SimpleFileVisitor<Path>(){

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                System.out.println("进入==>"+dir);
                dirCount.incrementAndGet();
                return super.preVisitDirectory(dir, attrs);
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println(file);
                if(file.toFile().getName().endsWith("jar")){
                    jarCount.incrementAndGet();
                }
                fileCount.incrementAndGet();
                return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                System.out.println("退出==>"+dir);
                return super.postVisitDirectory(dir, exc);
            }
        });
        System.out.println("fileCount="+fileCount);
        System.out.println("dirCount="+dirCount);
        System.out.println("jarCount="+jarCount);


    }
}
