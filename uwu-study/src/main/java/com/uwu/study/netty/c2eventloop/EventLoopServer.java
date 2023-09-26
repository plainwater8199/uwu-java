package com.uwu.study.netty.c2eventloop;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

@Slf4j
public class EventLoopServer {
    public static void main(String[] args) {
        //细分2：创建一个独立的EventLoopGroup，处理耗时较长的事件
        EventLoopGroup defaultGroup = new DefaultEventLoopGroup();//处理普通任务和定时任务

        new ServerBootstrap()
                // netty 建议将EventLoop划分的更细；细分1
                // boss：只负责ServerSocketChannel上的accept事件 可以不定义数量，与下面的channel绑定
                // work：只负责socketChannel上的读写，如果不定义数量，则默认系统的线程数 * 2
                .group(new NioEventLoopGroup(), new NioEventLoopGroup(2))//定义work的线程数为2
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
//                        nioSocketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter(){
//                            @Override
//                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {//msg 是ByteBuf
//                                ByteBuf byteBuf = (ByteBuf)msg;
//                                log.debug(byteBuf.toString(Charset.defaultCharset()));
//                            }
//                        });
                        //将消息让handler1处理后然后再让handler2处理
                        nioSocketChannel.pipeline().addLast("handler1",new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf byteBuf = (ByteBuf) msg;
                                log.debug(byteBuf.toString(Charset.defaultCharset()));
                                ctx.fireChannelRead(msg);//将消息传递给下一个handler
                            }
                        }).addLast(defaultGroup,"handler2",new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf byteBuf = (ByteBuf) msg;
                                log.debug(byteBuf.toString(Charset.defaultCharset()));
                            }
                        });
                    }
                }).bind(8080);
    }
}
