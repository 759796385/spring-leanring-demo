package com.newtonk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/6/16 0016
 */
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/session")
//@SessionAttributes("pet")
public class SessionController {

    @Autowired
    private HttpSession httpSession;

    /**
     * 设置session
     */
    @GetMapping(value = "")
    public void setSession(){
        httpSession.setAttribute("aaa","123");
    }

    /**
     * 当session中不存在aaa 就会报session属性不存的异常
     */
    @GetMapping(value = "/get")
    public String getSession(@SessionAttribute String aaa){
        return aaa;
    }

    @GetMapping("/cookie")
    public String displayCookie(@CookieValue("JSESSIONID") String cookie){
        return cookie;
    }
}
