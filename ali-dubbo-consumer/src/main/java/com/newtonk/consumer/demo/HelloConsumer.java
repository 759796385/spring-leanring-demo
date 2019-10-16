package com.newtonk.consumer.demo;

import com.newtonk.provider.IHelloService;
import org.apache.dubbo.config.annotation.Reference;

import org.springframework.stereotype.Component;

/**
 * 类名称：
 * 类描述：
 * @author：qiang.tang
 * 创建日期：2019/8/29
 */

@Component
public class HelloConsumer {
	@Reference( url = "dubbo://127.0.0.1:12345")
	private IHelloService iHelloService;

	public void consumer(){
		String result = iHelloService.doSome();
		System.out.println(result);
	}

}
