package com.fzz.IO.blockingModel;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import static com.fzz.IO.utils.ByteBufferUtil.debugRead;

@Slf4j
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ByteBuffer buffer = ByteBuffer.allocate(16);
        serverSocketChannel.bind(new InetSocketAddress(8080));
        List<SocketChannel> channelList=new ArrayList<>();

        while(true){
            //没有收到客户端连接会阻塞
            SocketChannel socketChannel = serverSocketChannel.accept();
            channelList.add(socketChannel);
            for(SocketChannel channel:channelList){
//                    while(true){
//                        int len = item.read(buffer);
//                        if(len==-1){
//                            break;
//                        }
//                        buffer.flip();
//                        while(buffer.hasRemaining()){
//                            System.out.print((char) buffer.get());
//                        }
//                        buffer.clear();
//                    }
                // 5. 接收客户端发送的数据
                log.debug("before read... {}", channel);
                channel.read(buffer); // 阻塞方法，线程停止运行
                buffer.flip();
                debugRead(buffer);
                buffer.clear();
                log.debug("after read...{}", channel);
            }
        }





    }
}
