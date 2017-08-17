package com.newtonk.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/8/16 0016
 */
@RestController
@RequestMapping(value = "/about")
public class AccessController {

    @GetMapping(value = "")
    public String hello(){
        return "hello world!";
    }
}
