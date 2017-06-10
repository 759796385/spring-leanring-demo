package com.newtonk.app.service.impl;


import com.newtonk.app.service.Axe;
import com.newtonk.app.service.Person;

public class Chinese implements Person {
	private Axe axe;

	public Chinese() {
		System.out.println("=====无参构造器====");
	}

	public void setAxe(Axe axe) {
		this.axe = axe;
	}

	public void setTest(String name) {
		System.out.println("调用set方法进行中注入~~" + name);
	}

	@Override
	public void useAxe() {
		System.out.println(axe.chop());
	}

}
