package com.rogge.test.io;

import java.net.Socket;
import java.util.Date;
import java.util.concurrent.*;

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
public class IOClientTest {

    public static void main(String[] args) throws InterruptedException {
        IOClientTest lTest = new IOClientTest();
        ExecutorService lService = Executors.newCachedThreadPool();
        ThreadPoolExecutor lExecutor = new ThreadPoolExecutor(5, 10, 20, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(20));
        for (; ; ) {
            try {
                lService.execute(lTest.new SendData());
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class SendData implements Runnable {

        @Override
        public void run() {
            try {
                Socket socket = new Socket("127.0.0.1", 3333);
                socket.getOutputStream().write((new Date() + ": hello world").getBytes());
                System.out.println("发送数据" + (new Date() + ": hello world"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
