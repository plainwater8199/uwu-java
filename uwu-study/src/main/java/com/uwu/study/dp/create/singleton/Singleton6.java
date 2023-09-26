package com.uwu.study.dp.create.singleton;

/**
 * lazy loading（懒汉式）--- 双重检查
 * 虽然达到类按需初始化的目的，但是却带来线程不安全的问题
 * 可以通过synchronized解决，但是带来了效率下降问题
 *
 *
 * 多线程下不能实现同步，
 */
public class Singleton6 {
    //volatile 防止语句重排
    private static volatile Singleton6 INSTANCE;

    private Singleton6(){}

    public static Singleton6 getInstance(){
        if(INSTANCE == null){//多线程这里出现问题。 --- 第一次检查保证只有首次并发的情况下才阻塞，提高性能
            //妄图通过减少同步代码库的方式提高效率，然而不行
            synchronized (Singleton6.class){ // 保证线程安全
                if(INSTANCE == null){ //-- 第二次检查是保证避免创建重复对象
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    INSTANCE = new Singleton6();
                }
            }
        }
        return INSTANCE;
    }

    public static void main(String[] args) {
        for (int i= 0;i<100;i++){
            new Thread(()->{
                System.out.println(Singleton6.getInstance().hashCode());
            }).start();
        }
    }
}
