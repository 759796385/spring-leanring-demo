package com.newtonk.thread;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;

public class LogService {
	private final BlockingQueue<String> queue = null;
	private final LoggerThread loggerThread = null;
	private final PrintWriter writer = null;

	private boolean isShutdown;
	private int reservcations;

	public void start() {
		loggerThread.start();
	}

	public void stop() {
		synchronized (this) {
			isShutdown = true;
		}
		loggerThread.interrupt();
	}

	/**
	 * 生产者
	 * 
	 * @param msg
	 * @throws InterruptedException
	 */
	public void log(String msg) throws InterruptedException {
		synchronized (this) {
			if (isShutdown) {
				throw new IllegalStateException();
			}
			reservcations++;
		}
	}

	/**
	 * 消费者
	 *
	 */
	private class LoggerThread extends Thread {
		public void run() {
			try {
				while (true) {
					try {
						synchronized (LogService.this) {
							if (isShutdown && reservcations == 0) {
								break;
							}
							String msg = queue.take();
							synchronized (LogService.this) {
								reservcations--;
							}
							writer.println(msg);
						}
					} catch (InterruptedException e) {
						// ����
					}
				}
			} finally {
				writer.close();
			}
		}
	}
}
