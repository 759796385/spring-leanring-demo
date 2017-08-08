package com.newtonk.thread.threadsafe;

import java.util.concurrent.CountDownLatch;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/8/5 0005
 */
public class LockTest {
    public static void main(String[] args) throws InterruptedException{
        System.out.println(timeTasks(5, new Runnable() {
            @Override
            public void run() {
                System.out.println("11");
            }
        }));

    }

    public static long timeTasks(int Threads, final Runnable task)
            throws InterruptedException {
        // 起始门
        final CountDownLatch startGate = new CountDownLatch(1);
        // 结束门
        final CountDownLatch endGate = new CountDownLatch(Threads);

        for (int i = 0; i <= Threads; i++) {
           new Thread(() -> {
               try {
                   startGate.await();   //计数器不为0就阻塞
                   try {
                       task.run();
                   } finally {
                       endGate.countDown();
                   }
               } catch (Exception ignored) {
               }
           }).start();
        }

        long start = System.nanoTime();
        startGate.countDown();  //减少计数器，1-> 0 所有startGate线程释放
        endGate.await();// 此时主线程阻塞， 直到到所有子线程完成
        long end = System.nanoTime();
        return end - start;
    }
}
