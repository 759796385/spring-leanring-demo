package com.newtonk.feign;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 类名称：
 * 类描述：
 * 创建人：newtonk
 * 创建日期：2017/12/12
 */
@Component
public class FeignClientFallback implements ProductClient {
    @Override
    public Date serviceDate() {
        System.out.println("默认断路器实现");
        return null;
    }
}
