package com.newtonk.provider;


import org.apache.dubbo.config.annotation.Service;


/**
 * 类名称：注意这个Service是dubbo包的。
 * 类描述：
 * @author：qiang.tang
 * 创建日期：2019/8/29
 */
@Service(stub = "com.newtonk.provider.IHelloServiceStub")
public class HelloServiceImpl implements IHelloService {


	@Override
	public String doSome() {
		return "hello world";
	}
}
