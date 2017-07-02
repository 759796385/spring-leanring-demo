package com.newtonk.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;

/**
 * 接口名称：spring-cloud-consumer下ProductController接口的定义
 * 接口描述：FeignClient中的值是一个任意的客户端名称。用于创建Ribbon负载均衡器。
 * 创建人：tq
 * 创建日期：2017/6/30 0030
 */
@FeignClient("eureka-consumer")
public interface ProductClient {

    /**
     * 表示对ProductController下的serviceDate 声明式调用
     */
    @GetMapping("/date")
    Date serviceDate();
}
