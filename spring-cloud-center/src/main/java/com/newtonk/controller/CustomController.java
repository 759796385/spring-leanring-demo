//package com.newtonk.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Date;
//
///**
// * 类名称：
// * 类描述：
// * 创建人：tq
// * 创建日期：2017/6/30 0030
// */
//@RestController
//public class CustomController {
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @GetMapping(value = "/data")
//    public Date getDate(){
//        return this.restTemplate.getForObject("http://localhost/v1/tmp/server_time",Date.class);
//    }
//}
