package com.newtonk.provider;

/**
 * 类名称：
 * 类描述：
 * @author：qiang.tang
 * 创建日期：2019/10/16
 */
public class IHelloServiceStub implements IHelloService {

	private IHelloService service;
	public IHelloServiceStub(IHelloService iHelloService){
		service = iHelloService;
	}
	@Override
	public String doSome() {
		System.out.println("我进来代理了");
		try {
			return service.doSome();
		}catch (Exception e){
			System.out.println("熔断降级");
			throw e;
		}
	}
}
