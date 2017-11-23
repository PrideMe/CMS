package com.util.test;

/**
 * Created by 22717 on 2017/11/22.
 * 线程测试
 */
public class ThreadTest extends Thread {
    private int ticket = 10;
    /**
     * If this thread was constructed using a separate
     * <code>Runnable</code> run object, then that
     * <code>Runnable</code> object's <code>run</code> method is called;
     * otherwise, this method does nothing and returns.
     * <p>
     * Subclasses of <code>Thread</code> should override this method.
     *
     * @see #start()
     * @see #stop()
     */
    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            if (this.ticket>0){
                System.out.println("线程："+Thread.currentThread().getName()+"卖票："+this.ticket--);
            }
        }
    }

    public static void main(String[] args) {
        ThreadTest threadTest = new ThreadTest();
        new Thread(threadTest,"济南站").start();
        new Thread(threadTest,"杭州站").start();
        //new Thread(threadTest,"c").start();
    }
}
