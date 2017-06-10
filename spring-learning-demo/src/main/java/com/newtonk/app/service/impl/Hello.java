package com.newtonk.app.service.impl;

import org.springframework.stereotype.Component;

@Component("hello")
public class Hello {
	public void foo() {
		System.out.println("执行hello组件的foo（）方法");
	}

	public String addUser(String name, String pass) {
		if (name.equals("sb")) {
			throw new IllegalArgumentException("不带这么侮辱人的！");
		}
		System.out.println("执行hello组件的AddUser添加用户" + name);
		return "我是addUser返回值啦:" + pass;
	}
}
