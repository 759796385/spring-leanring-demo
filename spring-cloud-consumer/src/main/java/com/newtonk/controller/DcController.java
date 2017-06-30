package com.newtonk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/6/30 0030
 */
@RestController
public class DcController {
    /**
     * 一个负载均衡客户端的抽象定义
     */
//    @Autowired
//    private LoadBalancerClient loadBalancerClient;
    /**
     * rest调用模板
     */
    @Autowired
    private RestTemplate restTemplate;

    /*
    * 方式1：通过Eureka调用指定节点，用的是url
    * */
//    @GetMapping("/consumer")
//    public String dc() {
//        /* 消费生产者的资源*/
//        ServiceInstance serviceInstance = loadBalancerClient.choose("eureka-product");
//        String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/dc";
//        System.out.println(url);
//        return restTemplate.getForObject(url, String.class);
//    }

    /**
     * 当Ribbon与Eureka联合使用，自动选取服务实例
     */
    @GetMapping("/consumer")
    public String cusumer(){
        return restTemplate.getForObject("http://eureka-product/dc", String.class);
    }
}
