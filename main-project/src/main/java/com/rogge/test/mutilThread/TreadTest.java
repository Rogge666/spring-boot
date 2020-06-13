package com.rogge.test.mutilThread;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * [Description]
 * <p>
 * [How to use]
 * <p>
 * [Tips]
 *
 * @author Created by Rogge on 2020-05-27.
 * @since 1.0.0
 */
public class TreadTest {

    public static void main(String[] args) {
        AtomicInteger lInteger = new AtomicInteger(4);
        lInteger.addAndGet(3);
        System.out.println(lInteger.get());
        ThreadPoolExecutor lThreadPoolExecutor = new ThreadPoolExecutor(1, 2, 20, TimeUnit.MILLISECONDS, new  ArrayBlockingQueue<Runnable>(2) {
        });

        lThreadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("----------Thread1 start--------"+Thread.currentThread());
                    Thread.sleep(2000);
                    System.out.println("----------Thread1 end--------"+Thread.currentThread());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        lThreadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("----------Thread2 start--------"+Thread.currentThread());
                    Thread.sleep(2000);
                    System.out.println("----------Thread2 end--------"+Thread.currentThread());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        lThreadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("----------Thread3 start--------"+Thread.currentThread());
                    Thread.sleep(5000);
                    System.out.println("----------Thread3 end--------"+Thread.currentThread());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        lThreadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("----------Thread4 start--------"+Thread.currentThread());
                    Thread.sleep(2000);
                    System.out.println("----------Thread4 end--------"+Thread.currentThread());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public class CusAqs extends AbstractQueuedSynchronizer{

    }
}
