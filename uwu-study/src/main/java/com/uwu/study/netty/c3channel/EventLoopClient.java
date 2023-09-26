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
@Slf4j
public class EventLoopClient {
    public static void main(String[] args) throws InterruptedException {
        // 带有future，promise的类型都是和异步方法配套使用，用来处理结果。
        ChannelFuture channelFuture = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new StringEncoder());
                    }
                }).connect(new InetSocketAddress("localhost", 8080));//连接到服务器，异步非阻塞: （main发起调用）异步，调用connect()的线程不关心结果，（真正执行connect()的是nio线程）处理由其它线程来做，非阻塞：发起调用后就继续运行。//连接延迟，
        //1、使用sync()来同步处理结果。调用sync()时阻塞，nio建立连接时恢复运行。
//        channelFuture.sync();// 如果无，就直接去获取channel，是未建立连接的channel。  sync():阻塞住当前线程（main），直到nio线程连接建立完毕
//        Channel channel = channelFuture.channel(); //main中执行
//        channel.writeAndFlush("water111");//main中执行

        //2、使用addListener(回调对象)方法异步处理结果
        channelFuture.addListener(new ChannelFutureListener() {
            //再nio线程连接建立后，会调用operationComplete()
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                Channel channel = channelFuture.channel();
                log.debug("{}",channel); //nio中执行
                channel.writeAndFlush("hello water"); //nio中执行
            }
        });

    }
}
