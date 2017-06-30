package com.newtonk.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/6/30 0030
 */
@RestController
@Slf4j
public class ProductController {

    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 通过DiscoveryClient对象，在日志中打印出服务实例的相关内容。
     */
    @GetMapping("/dc")
    public String dc() {
        String services = "Services: " + discoveryClient.getServices();
        log.info(services);
        return services;
    }
}
