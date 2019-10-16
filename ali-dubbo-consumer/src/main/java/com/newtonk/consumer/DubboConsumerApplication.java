package com.newtonk.consumer;

import com.newtonk.consumer.demo.HelloConsumer;

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
		doSome(context);
	}

	public static void doSome(ConfigurableApplicationContext context){
		HelloConsumer helloConsumer = context.getBean(HelloConsumer.class);
		helloConsumer.consumer();
	}
}
