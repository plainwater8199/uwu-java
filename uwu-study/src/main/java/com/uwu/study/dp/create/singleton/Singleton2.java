package com.uwu.study.dp.create.singleton;

/**
 * 和1一样的写法
 * 使用静态代码块
 */
public class Singleton2 {

    private static final Singleton2 INSTANCE;
    static{
        INSTANCE = new Singleton2();
    }

    private Singleton2(){};

    public static Singleton2 getInstance(){
        return INSTANCE;
    }

    public void say(String words){
        System.out.println("say:"+words);
    }

    public static void main(String[] args) {
        Singleton2 s1 = Singleton2.getInstance();
        Singleton2 s2 = Singleton2.getInstance();
        System.out.println(s1 == s2);

    }
}
