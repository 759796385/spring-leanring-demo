package com.newtonk.thread.exchange;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

public class ExchangeTest {

	public static void main(String[] args) {
		final Exchanger<List<Integer>> exchanger = new Exchanger<List<Integer>>();
		new Thread() {
			public void run() {
				List<Integer> l = new ArrayList<Integer>();
				l.add(1);
				l.add(3);
				try {
					l = exchanger.exchange(l);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("thread1" + l);
			};
		}.start();

		new Thread() {
			public void run() {
				List<Integer> l = new ArrayList<Integer>();
				l.add(2);
				l.add(4);
				try {
					l = exchanger.exchange(l);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("thread2" + l);
			};
		}.start();
	}
}
