package com.fzz.IO.blockingModel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class Client {
    public static void main(String[] args) throws IOException{
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost",8080));
//            sc.write(Charsets.UTF_8.encode("你好helloWorld"));
        System.out.println("客户端创立连接");
    }
}
