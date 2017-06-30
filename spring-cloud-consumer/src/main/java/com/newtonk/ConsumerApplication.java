package com.newtonk;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 类名称： 消费者
 * 类描述：主类中通过加上@EnableDiscoveryClient注解，该注解能激活Eureka中的DiscoveryClient实现
 * 创建人：tq
 * 创建日期：2017/6/30 0030
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ConsumerApplication {
    /**
     * 使用Ribbon来管理负载均衡
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(ConsumerApplication.class).web(true).run(args);
    }
}
