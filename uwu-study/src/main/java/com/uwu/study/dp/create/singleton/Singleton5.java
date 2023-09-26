package com.uwu.study.dp.create.singleton;

/**
 * lazy loading（懒汉式）
 * 虽然达到类按需初始化的目的，但是却带来线程不安全的问题
 * 可以通过synchronized解决，但是带来了效率下降问题
 *
 *
 * 多线程下不能实现同步，
 */
public class Singleton5 {
    private static Singleton5 INSTANCE;

    private Singleton5(){}

    public static Singleton5 getInstance(){
        if(INSTANCE == null){//多线程这里出现问题。
            //妄图通过减少同步代码库的方式提高效率，然而不行
            synchronized (Singleton5.class){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                INSTANCE = new Singleton5();
            }
        }
        return INSTANCE;
    }

    public static void main(String[] args) {
        for (int i= 0;i<100;i++){
            new Thread(()->{
                System.out.println(Singleton5.getInstance().hashCode());
            }).start();
        }
    }
}
