package com.newtonk.thread.synchronizedmethod;

public class SynchronizedMethod {
	public synchronized void method1() throws InterruptedException {
		System.out.println("方法1");
		Thread.sleep(5000);
		System.out.println("方法1执行完毕");
	}

	public synchronized void method2() throws InterruptedException {
		System.out.println("方法2");
		Thread.sleep(5000);
		System.out.println("方法2执行完毕");
	}

	public static void main(String[] args) {
		final SynchronizedMethod test = new SynchronizedMethod();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					test.method1();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					test.method2();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		t1.start();
		t2.start();
	}
}
