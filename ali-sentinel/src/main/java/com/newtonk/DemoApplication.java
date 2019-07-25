package com.newtonk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * 类名称：
 * 类描述：
 * @author：qiang.tang
 * 创建日期：2019/7/25
 */
@SpringBootApplication
@Configuration
public class DemoApplication {



	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(DemoApplication.class);
		ConfigurableApplicationContext context = app.run(args);

	}
}
