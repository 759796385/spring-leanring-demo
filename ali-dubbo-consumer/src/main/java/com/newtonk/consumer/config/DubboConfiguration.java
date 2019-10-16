package com.newtonk.consumer.config;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 类名称：
 * 类描述：
 * @author：qiang.tang
 * 创建日期：2019/10/16
 */
@Configuration
@DubboComponentScan
public class DubboConfiguration {
	/**
	 * 当前应用配置
	 */
	@Bean
	public ApplicationConfig dubboApplicationConfig() {
		ApplicationConfig applicationConfig = new ApplicationConfig();
		applicationConfig.setName("dubbo-annotation-consumer");
		return applicationConfig;
	}

	/**
	 * 当前连接注册中心配置
	 */
	@Bean
	public RegistryConfig registryConfig() {
		RegistryConfig registryConfig = new RegistryConfig();
		registryConfig.setAddress("N/A");
		return registryConfig;
	}

}
