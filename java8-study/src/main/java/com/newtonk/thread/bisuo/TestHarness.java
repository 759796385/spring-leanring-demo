package com.newtonk.thread.bisuo;

import java.util.concurrent.CountDownLatch;

public class TestHarness {
	public long timeTasks(int Threads, final Runnable task)
			throws InterruptedException {
		// 起始门
		final CountDownLatch startGate = new CountDownLatch(1);
		// 结束门
		final CountDownLatch endGate = new CountDownLatch(Threads);

		for (int i = 0; i < Threads; i++) {
			Thread t = new Thread() {
				public void run() {
					try {
						startGate.await();
						try {
							task.run();
						} finally {
							endGate.countDown();
						}
					} catch (Exception ignored) {
					}
				}
			};
			t.start();
		}

		long start = System.nanoTime();
		startGate.countDown();
		endGate.await();// 保证主线程阻塞到所有副线程完成
		long end = System.nanoTime();
		return end - start;
	}
}
