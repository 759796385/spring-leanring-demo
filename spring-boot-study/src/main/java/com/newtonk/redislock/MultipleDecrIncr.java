package com.newtonk.redislock;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2018/1/13 0013
 */
@Component
public class MultipleDecrIncr {
    @Resource(name="redisTemplate")
    private RedisTemplate<String,Integer> redisTemplate;

    private RedisAtomicInteger redisAtomicInteger;
    /** decr线程数量 */
    private static final int DECR_THREAD_COUNT = 50;
    /** incr线程数量 */
    private static final int INCR_THREAD_COUNT = 50;

    private static CountDownLatch begin = new CountDownLatch(DECR_THREAD_COUNT + INCR_THREAD_COUNT);

    private static CountDownLatch finish = new CountDownLatch(DECR_THREAD_COUNT + INCR_THREAD_COUNT);

    static final String KEY = "string:decr:incr";


    public void main() throws InterruptedException {
        redisAtomicInteger = new RedisAtomicInteger(KEY,redisTemplate.getConnectionFactory(),50);
        System.out.println("初试化:  " + redisAtomicInteger.get());
        for(int i = 0; i < DECR_THREAD_COUNT; ++i) {
            new DecrThread().start();
            begin.countDown();
        }

        for(int i = 0; i < INCR_THREAD_COUNT; ++i) {
            new IncrThread().start();
            begin.countDown();
        }

        finish.await();

        Integer value = redisAtomicInteger.get();
        System.out.println(value);


    }

    /**
     * decr命令线程
     */
    class DecrThread extends Thread {

        @Override
        public void run() {

            try {
                begin.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            System.out.println("递减 :" + redisAtomicInteger.addAndGet(-5));

            finish.countDown();
        }
    }

    /**
     * incr命令线程
     */
    class IncrThread extends Thread {

        @Override
        public void run() {

            try {
                begin.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("递增 :" + redisAtomicInteger.addAndGet(5));

            finish.countDown();
        }

    }
}
