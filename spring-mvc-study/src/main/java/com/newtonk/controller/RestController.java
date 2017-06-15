package com.newtonk.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.mvc.LastModified;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/6/15 0015
 */
@org.springframework.web.bind.annotation.RestController
@RequestMapping(value = "/rest/{rest_id}")
public class RestController implements LastModified {

    /**
     * 类级的URL模板变量 能和方法级的模板变量 一起使用
     * 支持HEAD请求
     */
    @GetMapping(value = "/{request_id}")
    public String main(@PathVariable(value = "rest_id") String restId,@PathVariable(value = "request_id") String requestId){
        return restId+ " - " + requestId;
    }

    /**
     * Same contract as for HttpServlet's {@code getLastModified} method.
     * Invoked <b>before</b> request processing.
     * <p>The return value will be sent to the HTTP client as Last-Modified header,
     * and compared with If-Modified-Since headers that the client sends back.
     * The content will only get regenerated if there has been a modification.
     *
     * @param request current HTTP request
     * @return the time the underlying resource was last modified, or -1
     * meaning that the content must always be regenerated
     * @see HandlerAdapter#getLastModified
     * @see HttpServlet#getLastModified
     */
    @Override
    public long getLastModified(HttpServletRequest request) {
        return new Date().getTime();
    }
}
