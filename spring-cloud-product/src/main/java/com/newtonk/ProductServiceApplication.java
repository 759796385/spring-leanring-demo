package com.newtonk;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 类名称：生产者
 * 类描述：主类中通过加上@EnableDiscoveryClient注解，该注解能激活Eureka中的DiscoveryClient实现
 * 创建人：tq
 * 创建日期：2017/6/30 0030
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ProductServiceApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ProductServiceApplication.class).web(true).run(args);
    }
}
