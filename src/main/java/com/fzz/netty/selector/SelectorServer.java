package com.fzz.netty.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

public class SelectorServer {
    public static void splitBuffer(ByteBuffer source){
        source.flip();
        int oldLimit = source.limit();
        for(int i=0;i<oldLimit;i++){
            if(source.get(i)=='\n'){
                int len=i+1-source.position();
                ByteBuffer buffer = ByteBuffer.allocate(len);
                source.limit(i+1);
                buffer.put(source);
                buffer.flip();
                String str = Charset.defaultCharset().decode(buffer).toString();
                System.out.println(str);
                buffer.clear();
                source.limit(oldLimit);
            }
        }
        source.compact();
    }

    public static void main(String[] args) throws IOException {
        //1：创建selector
        Selector selector = Selector.open();
        //2：创建channel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8080));
        serverSocketChannel.configureBlocking(false);
        //3：注册绑定至selector中
        SelectionKey sscKey = serverSocketChannel.register(selector,0,null);
        sscKey.interestOps(SelectionKey.OP_ACCEPT);

        while(true){
            //检查是否有事件，有事件往下，无事件阻塞
            selector.select();
            //所有的事件的selectionKey
            Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
            while(keys.hasNext()){
                SelectionKey key = keys.next();
                keys.remove();
                if(key.isAcceptable()){
                    ServerSocketChannel ssc= (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = ssc.accept();
                    socketChannel.configureBlocking(false);
                    ByteBuffer buffer = ByteBuffer.allocate(20);
                    SelectionKey scKey = socketChannel.register(selector, 0, buffer);
                    scKey.interestOps(SelectionKey.OP_READ);
                }else if(key.isReadable()){
                    try {
                        SocketChannel sc= (SocketChannel) key.channel();
                        ByteBuffer buffer= (ByteBuffer) key.attachment();
                        int read = sc.read(buffer);
                        if(read==-1){
                            key.cancel();
                        }else{
                            splitBuffer(buffer);
                            // 需要扩容
                            if (buffer.position() == buffer.limit()) {
                                ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity() * 2);
                                buffer.flip();
                                newBuffer.put(buffer); // 0123456789abcdef3333\n
                                key.attach(newBuffer);
                            }
                        }
                    } catch (IOException e) {
                        key.cancel();
                    }

                }

            }

        }
    }
}
