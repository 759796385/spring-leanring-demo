package com.newtonk.thread.bisuo;

public class Interrupt {
	public static void main(String[] args) throws InterruptedException {
		System.out.println("开始中断");
		System.exit(1);
		Thread thread = new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < 1000000000; i++) {
					System.out.println("i=" + i);
					if (Thread.interrupted()) {
						return;
					}
				}
			}
		});
		long time = 0;
		for (int i = 0; i < 10000000; i++) {
			if (i == 5000) {
				Thread.currentThread().interrupt();
			}
			if (i == 6000) {
				System.out.println("我还执行哦");
			}
		}
		try {
			time = System.currentTimeMillis();
			Thread.sleep(5000);
		} catch (Exception e) {
			System.out.println("清除中断状态");
		}
		System.out.println(System.currentTimeMillis() - time);
		System.out.println(Thread.interrupted());
		thread.start();
		Thread.currentThread().sleep(1000);
		thread.interrupt();
	}
}
