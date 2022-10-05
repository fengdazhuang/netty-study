package com.fzz.IO.writeSelector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;

public class WriteServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc  = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress(8080));
        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT,null);
        while(true){
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while(iterator.hasNext()){
                SelectionKey key = iterator.next();
                iterator.remove();
                if(key.isAcceptable()){
                    SocketChannel sc  = ssc.accept();
                    sc.configureBlocking(false);
                    SelectionKey scKey = sc.register(selector, SelectionKey.OP_READ, null);
                    StringBuilder stringBuilder=new StringBuilder();
                    for(int i=0;i<3000000;i++){
                        stringBuilder.append("a");
                    }
                    ByteBuffer buffer = Charset.defaultCharset().encode(stringBuilder.toString());
                    System.out.println(buffer.position());
                    int write = sc.write(buffer);
                    System.out.println(write);
                    if(buffer.hasRemaining()){
                        scKey.interestOps(scKey.interestOps()+SelectionKey.OP_WRITE);
                        scKey.attach(buffer);

                    }

                }else if(key.isWritable()){
                    ByteBuffer buffer= (ByteBuffer) key.attachment();
                    SocketChannel sc= (SocketChannel) key.channel();
                    int write = sc.write(buffer);
                    System.out.println(write);
                    if(!buffer.hasRemaining()){
                        key.interestOps(key.interestOps()-SelectionKey.OP_WRITE);
                        key.attach(null);
                    }
                }
            }
        }
    }
}
