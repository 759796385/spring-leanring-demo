package com.newtonk.thread.threadpool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/8/28 0028
 */
public class ThreadPoolDemo {
    public static void main(String[] args) throws InterruptedException {
        test1();
    }

    public static void test1() throws InterruptedException {
        ExecutorService executors = Executors.newFixedThreadPool(1);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        /**
         * {@link java.util.concurrent.ThreadPoolExecutor.runWorker() }
         * 对于Runnable任务直接抛异常，将不会影响线程池中线程的运行状态。
         * 对于其他封装的任务，如FutureTask，将异常委托给任务持有。
         * 之后线程返回线程池
         *
         * https://www.ibm.com/developerworks/cn/java/j-jtp0730/ 之中的线程池泄露问题指的是自创建的线程池。不适用于ThreadPoolExecutor
         */
        executors.execute((Runnable) () -> {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("抛异常");
            throw new RuntimeException("sa");
        });
        countDownLatch.countDown();
        Thread.sleep(1000);
        executors.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("1221");
            }
        });

    }
}
