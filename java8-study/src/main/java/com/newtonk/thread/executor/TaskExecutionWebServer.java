package com.newtonk.thread.executor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TaskExecutionWebServer {
	private static final int NTHREADS = 100;
	private static final Executor exec = Executors.newFixedThreadPool(NTHREADS);

	public static void main(String[] args) throws IOException {
		ServerSocket socket = new ServerSocket(80);
		while (true) {
			final Socket connetion = socket.accept();
			Runnable task = new Runnable() {
				@Override
				public void run() {
					// handleRequest(connetion);
				}
			};
			exec.execute(task);
		}
	}
}
