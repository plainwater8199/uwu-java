package com.uwu.study.dp.create.singleton;

/**
 * 饿汉式(线程安全)
 * 类加载到内存后，就实例化一个单例，JVM保证线程安全（简单适用，推荐应用）
 *
 * 唯一缺点：不管用到与否，类装载时就完成实例化（类不用，也会装载）
 */
public class Singleton1 {
    //在内存中定义一个静态到实例
    private static final Singleton1 INSTANCE = new Singleton1();
    //无参构造方式定义成私有的，可以防止其它类直接new该对象---其它class中不能直接Singleton1 singleton1 = new Singleton1();
    private Singleton1(){};
    //创建一个生成实例到方法--生成时永远只生成内存中的那个静态实例
    public static Singleton1 getInstance(){
        return INSTANCE;
    }
    //创建一个方法
    public void say(String words){
        System.out.println("says:"+words);
    }

    public static void main(String[] args) {
        //证明两个实例是同一个单例
        Singleton1 s1 = Singleton1.getInstance();
        Singleton1 s2 = Singleton1.getInstance();
        System.out.println(s1 == s2);

    }

}
