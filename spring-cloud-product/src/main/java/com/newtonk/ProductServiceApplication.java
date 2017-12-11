package com.newtonk;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;

/**
 * 类名称：生产者
 * 类描述：主类中通过加上@EnableDiscoveryClient注解，该注解能激活Eureka中的DiscoveryClient实现
 * Spring Cloud Feign是一套基于Netflix Feign实现的声明式服务调用客户端。它使得编写Web服务客户端变得更加简单。
 * 我们只需要通过创建接口并用注解来配置它既可完成对Web服务接口的绑定。
 * 它具备可插拔的注解支持，包括Feign注解、JAX-RS注解。它也支持可插拔的编码器和解码器。
 * 创建人：tq
 * 创建日期：2017/6/30 0030
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
public class ProductServiceApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ProductServiceApplication.class).web(true).run(args);
    }


    /*
    用于http basic认证
     */
    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor(){
        return new BasicAuthRequestInterceptor("user","123");
    }
}
