package com.rogge.test.mutilThread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * [Description]
 * <p>
 * [How to use]
 * <p>
 * [Tips]
 *
 * @author Created by Rogge on 2020-05-30.
 * @since 1.0.0
 */
public class ConsumerAndProducerTest {
    private static final int blockingSize = 10;
    ArrayBlockingQueue<String> mBlockingQueue = new ArrayBlockingQueue<String>(blockingSize);
    final ReentrantLock mLock = new ReentrantLock();


    public static void main(String[] args) {
        ConsumerAndProducerTest test = new ConsumerAndProducerTest();
        Consumer lConsumer = test.new Consumer();
        Producer lProducer = test.new Producer();
        lConsumer.start();
        lProducer.start();
    }

    class Consumer extends Thread {
        @Override
        public void run() {
            consum();
        }

        private void consum() {
            for (; ; ) {
                try {
                    Thread.sleep(1000);
                    mBlockingQueue.take();
                    System.out.println("从队列取走一个元素，队列剩余" + mBlockingQueue.size() + "个元素");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                }
            }
        }


    }

    class Producer extends Thread {
        @Override
        public void run() {
            produce();
        }

        private void produce() {
            for (; ; ) {
                try {
                    Thread.sleep(1000);
                    mBlockingQueue.put("aaaaaaa");
                    System.out.println("向队列取中插入一个元素，队列剩余空间：" + (blockingSize - mBlockingQueue.size()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                }
            }
        }

    }

}
