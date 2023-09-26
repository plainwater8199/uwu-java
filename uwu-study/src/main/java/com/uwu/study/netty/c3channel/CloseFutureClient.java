package com.uwu.study.netty.c3channel;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Scanner;

@Slf4j
public class CloseFutureClient {
    public static void main(String[] args) throws InterruptedException {

        NioEventLoopGroup group = new NioEventLoopGroup();
        ChannelFuture channelFuture = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new StringEncoder());
                    }
                }).connect(new InetSocketAddress("localhost", 8080));

        Channel channel = channelFuture.sync().channel();
        log.debug("{}",channel);
        //开启一个线程来接受控制台的输入
        new  Thread(()->{
            Scanner scanner = new Scanner(System.in);
            while (true){
                String line = scanner.nextLine();
                if("q".equals(line)) {//如果输入时"q"则直接退出。
                    channel.close(); // close() 异步方法！
//                    log.debug("处理关闭之后的操作！"); // 这里写时错误的。不同的线程执行
                    break;
                }
                channel.writeAndFlush(line);
            }
        },"input").start();
//        log.debug("处理关闭之后的操作！"); // 这里写时错误的。


        //获取CloseFuture对象，
        //1、同步处理关闭
        /*
        ChannelFuture closeFuture = channel.closeFuture();
        closeFuture.sync();
        log.debug("处理关闭之后的操作！"); // main线程打印
        */
        //2、异步处理关闭
        ChannelFuture closeFuture = channel.closeFuture();
        closeFuture.addListener((ChannelFutureListener) channelFuture1 -> {
            log.debug("处理关闭之后的操作！"); // nio线程打印---
            group.shutdownGracefully();//不是立刻停止，先拒绝新任务，再逐步关闭运行的线程
        });


    }
}
