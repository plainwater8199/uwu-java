package com.uwu.study.multithread.base;

public class MyThread extends Thread {

    public MyThread( String name) {
        super(name);
    }

    public  void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread()+ " is running,---" + i);
        }

    }


    public static void main(String[] args) {
        MyThread myThread1 = new MyThread("water1");
        MyThread myThread2 = new MyThread("water3");
        MyThread myThread3 = new MyThread("water3");

        myThread1.start();
        myThread2.start();
        myThread3.start();

    }
}
