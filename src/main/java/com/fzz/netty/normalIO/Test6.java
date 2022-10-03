package com.fzz.netty.normalIO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


/**
 * 文件夹复制
 *
 */
public class Test6 {
    public static void main(String[] args) throws IOException {
        String source="C:\\Users\\冯大壮\\Desktop\\community-management-system-master";
        String copy="C:\\Users\\冯大壮\\Desktop\\community-management-system-masteraaaa";

        Files.walk(Paths.get(source)).forEach(path->{
            try {
                String target=path.toString().replace(source,copy);
                if(Files.isDirectory(path)){
                    Files.createDirectory(Paths.get(target));
                }
                else if(Files.isRegularFile(path)){
                    Files.copy(path, Paths.get(target));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
