package com.fzz.IO.writeSelector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class WriteClient {
    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        SocketChannel sc = SocketChannel.open();
        sc.configureBlocking(false);
        sc.register(selector, SelectionKey.OP_READ+SelectionKey.OP_CONNECT);
        sc.connect(new InetSocketAddress("localhost",8080));
        int count=0;
        while(true){
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while(iterator.hasNext()){
                SelectionKey key = iterator.next();
                iterator.remove();
                if(key.isConnectable()){
                    System.out.println(sc.finishConnect());
                }else if(key.isReadable()){
                    ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
                    count+= sc.read(buffer);
                    buffer.clear();
                    System.out.println(count);
                }
            }
        }

    }
}
