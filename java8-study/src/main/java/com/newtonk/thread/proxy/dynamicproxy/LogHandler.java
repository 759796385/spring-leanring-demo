package com.newtonk.thread.proxy.dynamicproxy;

import com.newtonk.thread.proxy.stati.Calculator;
import com.newtonk.thread.proxy.stati.CalculatorImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


public class LogHandler implements InvocationHandler {
	Object ojb;

	public LogHandler() {
	}

	public LogHandler(Object ojb) {
		this.ojb = ojb;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		this.doBefore();
		ojb = method.invoke(ojb, args);
		this.doAfter();
		return ojb;
	}

	public void doBefore() {
		System.out.println("before");
	}

	public void doAfter() {
		System.out.println("after");
	}

	public void testDynamicProxy() {
		Calculator ca = new CalculatorImpl();
		LogHandler lh = new LogHandler(ca);
		Calculator proxy = (Calculator) Proxy.newProxyInstance(ca.getClass()
				.getClassLoader(), ca.getClass().getInterfaces(), lh);
		System.out.println(proxy.add(1, 1));
	}

	public static void main(String[] args) {
		LogHandler l = new LogHandler();
		l.testDynamicProxy();
	}
}
