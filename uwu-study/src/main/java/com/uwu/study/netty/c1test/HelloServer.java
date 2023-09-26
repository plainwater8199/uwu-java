package com.uwu.study.netty.c1test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloServer {
    public static void main(String[] args) {
        new ServerBootstrap()//1、启动器，负责组装netty组件，启动服务。
                .group(new NioEventLoopGroup())//2、BoosEventLoop，WorkerEventLoop(selector,thread),group组  。。。。 由某个EventLoop处理read事件，接受到ByteBuf
                .channel(NioServerSocketChannel.class)//3、选择服务器的ServerSocketChannel实现，OIO，BIO
                .childHandler(//4、boss 负责处理连接，worker(child) 负责处理读写，决定来worker(child)能执行哪些操作（handler-处理器）。
                        new ChannelInitializer<NioSocketChannel>() {//5、channel 代表和客户端进行数据读写端通道  Initializer初始化, 负责添加别的handler---具体添加哪些handler在initChannel中
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {//连接建立后，调用初始化方法
                        //6、具体添加来哪些handler，
                        nioSocketChannel.pipeline().addLast(new StringDecoder());//StringDecoder：解码，数据是字节传输 将ByteBuf转字符串
                        nioSocketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter(){// 自定义handler

                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {//读事件发生以后接受一个上行消息。
                                //打印消息
                                log.debug("{}",msg);
                            }
                        });
                    }
                }).bind(8080);//绑定监听端口
    }

}
