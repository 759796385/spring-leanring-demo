package com.newtonk.provider;


import org.apache.dubbo.config.annotation.Service;


/**
 * 类名称：
 * 类描述：
 * @author：qiang.tang
 * 创建日期：2019/8/29
 */
@Service
public class HelloServiceImpl implements IHelloService {


	@Override
	public String doSome() {
		return "hello world";
	}
}
