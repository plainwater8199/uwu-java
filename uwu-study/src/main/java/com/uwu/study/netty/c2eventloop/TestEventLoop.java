package com.uwu.study.netty.c2eventloop;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class TestEventLoop {
    public static void main(String[] args) {
        //1、创建事件循环组
        EventLoopGroup group = new NioEventLoopGroup(2);//可处理：1-io事件、2-普通任务、3-定时任务   2-指定线程数
//        EventLoopGroup defaultGroup = new DefaultEventLoopGroup();//处理：普通任务，定时任务

        System.out.println(group.next());
        System.out.println(group.next());
        System.out.println(group.next());
        System.out.println(group.next());

        //3-执行普通任务，--异步
//        group.next().submit(()->{
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            log.debug("ok");
//        });





        //4-定时任务
        group.next().scheduleAtFixedRate(()->{
            log.debug("ok");
        },1,1, TimeUnit.SECONDS);





        log.debug("main");


//        System.out.println(NettyRuntime.availableProcessors());//读取电脑的线程数
    }
}
