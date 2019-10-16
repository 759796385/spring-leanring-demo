package com.newtonk;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 类名称：
 * 文档：http://dubbo.apache.org/zh-cn/blog/dubbo-annotation-driven.html
 * 类描述：
 * @author：qiang.tang
 * 创建日期：2019/8/29
 */
@SpringBootApplication
public class DubboProviderApplication {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(DubboProviderApplication.class);
		ConfigurableApplicationContext context = app.run(args);
	}
}
