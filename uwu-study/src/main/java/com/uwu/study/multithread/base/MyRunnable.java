package com.uwu.study.multithread.base;

import java.util.concurrent.atomic.AtomicInteger;

public class MyRunnable implements Runnable {
    AtomicInteger count = new AtomicInteger(20);

    @Override
    public void run() {
        while (count.get() > 0) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " -----还剩下： " + count.decrementAndGet());
        }
    }

    public static void main(String[] args) {
        MyRunnable myRunnable = new MyRunnable();
        Thread thread1 = new Thread(myRunnable, "线程1");
        Thread thread2 = new Thread(myRunnable, "线程2");
        Thread thread3 = new Thread(myRunnable, "线程3");
        Thread thread4 = new Thread(myRunnable, "线程4");
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }
}
