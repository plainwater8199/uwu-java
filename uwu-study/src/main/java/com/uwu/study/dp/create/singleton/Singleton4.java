package com.uwu.study.dp.create.singleton;

/**
 * lazy loading（懒汉式）
 * 虽然达到类按需初始化的目的，但是却带来线程不安全的问题
 * 可以通过synchronized解决，但是带来了效率下降问题
 */
public class Singleton4 {
    private static Singleton4 INSTANCE;
    private Singleton4(){};
    //synchronized 锁定的是当前对象
    // static synchronized 锁定是当前的class文件，即Singleton4.class
    public static synchronized Singleton4 getInstance(){
        if(INSTANCE == null){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            INSTANCE = new Singleton4();
        }
        return INSTANCE;
    }

    public void say(String words){
        System.out.println("says:"+words);
    }

    public static void main(String[] args) {
        for (int i = 0;i<100;i++){
            new Thread(()->{
                System.out.println(Singleton4.getInstance().hashCode());
            }).start();
        }
    }
}
