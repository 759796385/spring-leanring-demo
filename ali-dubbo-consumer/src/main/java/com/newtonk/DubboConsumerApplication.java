package com.newtonk;

import com.newtonk.consumer.HelloConsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 类名称：
 * 类描述：
 * @author：qiang.tang
 * 创建日期：2019/8/29
 */
@SpringBootApplication
public class DubboConsumerApplication {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(DubboConsumerApplication.class);
		ConfigurableApplicationContext context = app.run(args);
		HelloConsumer helloConsumer = context.getBean(HelloConsumer.class);
		helloConsumer.consumer();
	}
}
