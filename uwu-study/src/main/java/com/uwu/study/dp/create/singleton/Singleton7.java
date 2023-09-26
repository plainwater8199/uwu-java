package com.uwu.study.dp.create.singleton;

/**
 * 静态内部类方法
 * JVM保证单例 Singleton7只加载一次，Singleton7Holder只加载一次，INSTANCE只加载一次
 * 加载外部类时不会加载内部类，这样可以实现懒加载
 *
 *
 * 只加载Singleton7这个类时，Singleton7Holder不会被加载
 */
public class Singleton7 {
    //保证只有在内部类中才能访问，其它访问不了
    private Singleton7(){}
    //定义一个静态的内部类
    private static class Singleton7Holder{
        //在静态内部类初始化Singleton7
        private final static Singleton7 INSTANCE = new Singleton7();
    }
    //共有方法返回静态内部类里面的Singleton7
    public static Singleton7 getInstance(){
        return Singleton7Holder.INSTANCE;
    }

    public static void main(String[] args) {
        for(int i = 0; i<100;i++){
            new Thread(()->{
                System.out.println(Singleton7.getInstance().hashCode());
            }).start();
        }
    }

}
