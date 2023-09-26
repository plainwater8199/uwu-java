package com.uwu.study.dp.create.singleton;

/**
 * 枚举单例
 * 不仅可以解决线程同步，还可以防止反序列化
 *
 * 枚举没有构造方法--反编译不行？？？？
 */
public enum Singleton8 {
    INSTANCE;

    public static void main(String[] args) {
        for(int i = 0; i<100;i++){
            new Thread(()->{
                System.out.println(Singleton8.INSTANCE.hashCode());
            }).start();
        }
    }

}
