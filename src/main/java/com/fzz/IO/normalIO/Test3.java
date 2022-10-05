package com.fzz.IO.normalIO;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 *
 * 文件复制的两个方式：
 * 1 channel层次，transferTo方法
 * 2 Files的copy方法，传入的是两个文件得到路径，如果覆盖文件，可加入第三个参数：StandardCopyOption.REPLACE_EXISTING.
 *
 */
public class Test3 {
    public static void main(String[] args) {
        try {
/*            FileChannel in = new FileInputStream("from.txt").getChannel();
            FileChannel out = new FileOutputStream("to.txt").getChannel();
            in.transferTo(0,in.size(),out);*/

            Path fromPath = Paths.get("from.txt");
            Path toPath = Paths.get("to.txt");
            Files.copy(fromPath,toPath, StandardCopyOption.REPLACE_EXISTING);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
