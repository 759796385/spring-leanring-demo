package com.newtonk.thread.proxy.stati;

public class CalculatorProxy implements Calculator {
	Calculator cal;

	public CalculatorProxy(Calculator cal) {
		this.cal = cal;
	}

	@Override
	public int add(int a, int b) {
		System.out.println("qian");
		int result = cal.add(a, b);
		System.out.println("hou");
		return result;
	}
}
