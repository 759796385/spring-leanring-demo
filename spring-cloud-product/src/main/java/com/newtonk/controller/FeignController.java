package com.newtonk.controller;

import com.newtonk.feign.ProductClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/6/30 0030
 */
@Slf4j
@RestController
@RequestMapping("/remote")
public class FeignController {
    @Autowired
    private ProductClient productClient;

    @GetMapping(value = "/date")
    public Date getRemoteDate(){
        Date date =  productClient.serviceDate();
        log.info(" feign 声明式调用外部接口 response：" +date);
        return  date;
    }
}
