package com.fzz.netty.twoway;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;


import java.nio.charset.Charset;
import java.util.Scanner;

public class TwoWayClient {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        Channel channel = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new StringEncoder());
                        nioSocketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {

                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buffer= (ByteBuf) msg;
                                System.out.println(buffer.toString(Charset.defaultCharset()));
                            }
                        });
                    }
                })
                .connect("localhost", 8080)
                .sync()
                .channel();

        new Thread(()->{
            Scanner scanner = new Scanner(System.in);
            while(true){
                String s = scanner.nextLine();
                if(s.equals("q")){
                    channel.close();
                    break;
                }
                channel.writeAndFlush(s);
            }
        }).start();



        channel.closeFuture().addListener((ChannelFutureListener) channelFuture -> group.shutdownGracefully());

    }
}
