package com.newtonk.spi;

import java.util.ServiceLoader;

/**
 * 类名称：SPI 全称为 (Service Provider Interface) ,是JDK内置的一种服务提供发现机制。
 *  用它来做服务的扩展发现，它就是一种动态替换发现的机制
 * 类描述：
 * 创建人：qiang.tang
 * 创建日期：2018/10/23
 */
public class SPITest {
	public static void main(String[] args) {
		ServiceLoader<BaseInterface> loader = ServiceLoader.load(BaseInterface.class);
		for (BaseInterface baseInterface : loader) {
			//jijizhazha!!!!
			baseInterface.doSome();
			//com.newtonk.spi.impl.BirdBase
			System.out.println(baseInterface.getClass().getName());
		}
	}
}
