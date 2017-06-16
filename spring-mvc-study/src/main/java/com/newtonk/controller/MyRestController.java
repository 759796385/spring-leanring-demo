package com.newtonk.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/6/16 0016
 */
@RestController
@RequestMapping("/")
public class MyRestController {

    @RequestMapping("/entity")
    public ResponseEntity<String> handle(HttpEntity<byte[]> requestEntity) throws UnsupportedEncodingException {
        //通过HttpEntity可访问request中的head和body
        String requestHeader = requestEntity.getHeaders().getFirst("MyRequestHeader");
        byte[] requestBody = requestEntity.getBody();
        char[] stringChar = new char[requestBody.length];
        for (int i=0;i< requestBody.length; i++) {
            stringChar[i] = (char)requestBody[i];
        }
        System.out.println("request 内容: " + String.valueOf(stringChar));
        // do something with request header and body
        //ResponseEntity 可自定义返回内容的head和body
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("MyResponseHeader", "MyValue");
        return new ResponseEntity<String>("Hello World", responseHeaders, HttpStatus.CREATED);
    }
}
