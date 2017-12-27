package com.newtonk;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/12/13 0013
 */
public class ConcurrentHashMapDemo {

    private Map<Integer, Long> cache = new ConcurrentHashMap<>(16);

    public static void main(String[] args) {
        ConcurrentHashMapDemo ch = new ConcurrentHashMapDemo();
        System.out.println(ch.fibonaacci(80));
    }

    public long fibonaacci(Integer i) {
        if (i == 0 | i == 1) {
            return i;
        }
        return cache.computeIfAbsent(i, (key) -> {
            System.out.println("fibonaacci : "+key);
            return fibonaacci(key - 1)+ fibonaacci(key-2);
        });

    }
}
