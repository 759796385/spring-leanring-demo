package com.newtonk.thread.atomics;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用Atomic类来实现原子操作，核心是通过JNI的方式使用硬件支持的CAS指令
 * 
 * @author lenovo
 *
 */
public class Count {
	private AtomicInteger integer = new AtomicInteger();

	public int add() {
		return integer.incrementAndGet();
	}

	public int jian() {
		return integer.decrementAndGet();
	}

	public static void main(String[] args) {
		Count c = new Count();
		System.out.println(c.add());
		System.out.println(c.jian());
	}
}
