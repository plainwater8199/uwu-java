package com.uwu.study.dp.structure.proxy;

/**
 * 代理模式：由于某些圆需要给某个对象提供一个代理以控制对该对象的访问，这时访问对象不适合或者不能直接引用目标对象，代理对象作为访问对象和目标对象的中介。
 *
 * 优点：
 * 1、代理模式子啊客户端和目标对象之间起一个中介作用和保护目标对象的作用；
 * 2、代理对象可以扩展目标对象的功能；
 * 3、代理模式能将客户端和目标对象分离，在一定程度上降低了系统的耦合度
 * 缺点：
 * 1、增加代理对象会是处理速度变慢
 * 2、增加了系统的复杂度。
 */
public class proxyPatternTest {
    public static void main(String[] args) {
        RealSubject realSubject = new RealSubject();
        Proxy proxy = new Proxy(realSubject);

        proxy.buy();
    }
}
