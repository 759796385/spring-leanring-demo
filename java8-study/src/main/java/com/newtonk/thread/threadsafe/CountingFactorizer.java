package com.newtonk.thread.threadsafe;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by newtonk on 2017/6/29.
 */
public class CountingFactorizer {
    private final AtomicLong integer = new AtomicLong(0);
    public long getCount(){return integer.incrementAndGet();}


    public static void main(String[] args) {
        CountingFactorizer c1 = new CountingFactorizer();
        System.out.println(c1.getCount());
    }
}
