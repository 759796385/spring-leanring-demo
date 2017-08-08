package com.newtonk.thread.future;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class FutureTest {
	public void test1() throws InterruptedException, ExecutionException {
		Future<HashMap> future = getDateFromRemote2();
		HashMap date = (HashMap) future.get();
	}

	private Future<HashMap> getDateFromRemote2() {
		// return threadPool.submit(new Callble<HashMap>(){
		// public HashMap call() throws Exception{
		// return getDateFromRemote2();
		// };
		return null;
	}
}
