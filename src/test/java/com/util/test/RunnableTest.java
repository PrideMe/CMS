package com.util.test;

/**
 * Created by 22717 on 2017/11/22.
 * 线程测试
 */
public class RunnableTest implements Runnable {
    private int ticket = 10;
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
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
