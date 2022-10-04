package com.fzz.netty.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static com.fzz.netty.utils.ByteBufferUtil.debugRead;

@Slf4j
public class Server {
    public static void main(String[] args) throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ByteBuffer buffer = ByteBuffer.allocate(16);
        serverSocketChannel.bind(new InetSocketAddress(8080));
        serverSocketChannel.configureBlocking(false);
        List<SocketChannel> channelList=new ArrayList<>();

        while(true){
            //没有收到客户端连接会阻塞
            SocketChannel socketChannel = serverSocketChannel.accept();
            if(socketChannel!=null){
                socketChannel.configureBlocking(false);
                channelList.add(socketChannel);
            }
            for(SocketChannel channel:channelList){
                int len = channel.read(buffer);// 阻塞方法，线程停止运行
                if(len>0){
                    buffer.flip();
                    String string = Charset.defaultCharset().decode(buffer).toString();
                    System.out.println(string);
                    buffer.clear();
                    log.debug("after read...{}", channel);
                }

            }
        }





    }
}
