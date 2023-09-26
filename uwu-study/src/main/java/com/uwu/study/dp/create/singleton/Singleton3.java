package com.uwu.study.dp.create.singleton;

/**
 * 懒加载模式（线程不安全）
 * 虽然达到类按需初始化到目的，但是却带来类线程不安全问题
 */
public class Singleton3 {
    //最开始不初始化
    //如果加final就会出错。被final修饰的对象必须进行初始化。
    private static Singleton3 INSTANCE;
    //将无参构造方法私有化，不让其它类进行创建
    private Singleton3(){};
    //调用getInstance()方法时进行初始化
    public static Singleton3 getInstance(){
        if(INSTANCE == null){//判断为null时初始化。如果不为null，直接返回。
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            INSTANCE = new Singleton3();
        }
        return INSTANCE;
    }

    public void say(String words){
        System.out.println("say:"+words);
    }

    public static void main(String[] args) {
        for(int i = 0;i<100;i++){
            new Thread(()->{
                System.out.println(Singleton3.getInstance().hashCode());//同一个类的不同对象，hash码是不同的。
            }).start();
        }
    }
}
