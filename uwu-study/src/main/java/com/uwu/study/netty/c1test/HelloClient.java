package com.uwu.study.netty.c1test;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

public class HelloClient {
    public static void main(String[] args) throws InterruptedException {
        new Bootstrap()//启动类
                .group(new NioEventLoopGroup())//添加EventLoop
                .channel(NioSocketChannel.class)//选择客户端channel实现
                .handler(new ChannelInitializer<NioSocketChannel>() {//添加处理器
                    //在连接建立后被调用
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new StringEncoder());//转码，将字符串转成ByteBuf
                    }
                }).connect(new InetSocketAddress("localhost",8080))
                .sync()//阻赛方法，直到连接成功
                .channel()//代表连接对象
                .writeAndFlush("hello water");//向服务器发送数据
    }
}
