package com.newtonk.thread;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class LogService {
	private final BlockingQueue<String> queue = new LinkedBlockingDeque<>();
	private final LoggerThread loggerThread = new LoggerThread();

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

	public static void main(String[] args) {
        LogService logService = new LogService();
        logService.start();
        Scanner sc=new Scanner(System.in);
        while (sc.hasNextLine()){
            String a = sc.nextLine();
            if(a.equals("end")){
                break;
            }
            try {
                logService.log(a);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        logService.stop();
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
            queue.put(msg);
		}
	}

	/**
	 * 消费者
	 *
	 */
	private class LoggerThread extends Thread {
		public void run() {
            while (true) {
                try {
                    if (isShutdown && reservcations == 0) {
                        break;
                    }
                    String msg = queue.take();
                    synchronized (LogService.this) {
                        reservcations--;
                    }
                    System.out.println(msg);
                } catch (InterruptedException e) {
                    // 处理中断异常
                }
            }
		}
	}
}
