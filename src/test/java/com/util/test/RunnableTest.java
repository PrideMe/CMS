package com.util.test;

/**
 * Created by 22717 on 2017/11/22.
 * 线程测试
 */
public class RunnableTest implements Runnable {
    private int ticket = 10;
    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            if (this.ticket>0){
                System.out.println("线程："+Thread.currentThread().getId()+"卖票："+this.ticket--);
            }
        }
    }

    public static void main(String[] args) {
        RunnableTest runnableTest = new RunnableTest();
        new Thread(runnableTest).start();
        new Thread(runnableTest).start();
        new Thread(runnableTest).start();
    }
}
