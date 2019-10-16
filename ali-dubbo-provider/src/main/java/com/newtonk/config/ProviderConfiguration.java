package com.newtonk.config;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.ProviderConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 类名称： 推荐使用bean配置 取代xml配置
 * 类描述：
 * 文档http://dubbo.apache.org/zh-cn/blog/dubbo-annotation-driven.html
 * @author：qiang.tang
 * 创建日期：2019/10/16
 */
@Configuration
@DubboComponentScan("com.newtonk.provider")
public class ProviderConfiguration {

	/**
	 * 当前应用配置
	 * 取代dubbo:application xml配置
	 */
	@Bean("dubbo-annotation-provider")
	public ApplicationConfig applicationConfig() {
		ApplicationConfig applicationConfig = new ApplicationConfig();
		applicationConfig.setName("dubbo-annotation-provider");
		return applicationConfig;
	}

	/**
	 * 当前连接注册中心配置
	 * 取代dubbo:registry
	 */
	@Bean("my-registry")
	public RegistryConfig registryConfig() {
		RegistryConfig registryConfig = new RegistryConfig();
		registryConfig.setAddress("N/A");
		return registryConfig;
	}

	/**
	 * 提供方配置
	 * 去掉 dubbo:provider
	 */
	@Bean
	public ProviderConfig ReferenceConfig(){
		ProviderConfig providerConfig = new ProviderConfig();
		providerConfig.setFilter("my");
		return providerConfig;
	}

	/**
	 * 当前连接注册中心配置
	 * 取代dubbo:protocol
	 */
	@Bean("dubbo")
	public ProtocolConfig protocolConfig() {
		ProtocolConfig protocolConfig = new ProtocolConfig();
		protocolConfig.setName("dubbo");
		protocolConfig.setPort(12345);
		return protocolConfig;
	}

}
