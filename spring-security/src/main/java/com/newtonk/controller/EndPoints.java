package com.newtonk.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/8/17 0017
 */
@RestController
public class EndPoints {
    @GetMapping("/product/{id}")
    public String getProduct(@PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "product id : " + id;
    }

    /** 注意：SecurityContextHolder是最基本的对象，这个对象存储了当前应用程序上下文的详细信息，主要是包含的主体(principal)的信息。
    * 默认情况下SecurityContextHolder使用ThreadLocal存储信息，保证信息是线程安全的。
    */


    @GetMapping("/order/{id}")
    public String getOrder(@PathVariable String id) {
        getUserName();
        return "order id : " + id;
    }

    /**
     * UserDetailsService只有一个方法。
     * 这是在Spring Security中为用户加载信息最常用的方法，您将看到在需要用户信息时，它将在整个框架中使用（用来加载用户信息）。
     * 成功鉴权后，UserDetails 用来构建Authentication 对象，并存储在SecurityContextHolder之中。
     * 我们提供了一些UserDetailsService的实现。有内存映射map(InMemoryDaoImpl),JDBC(JdbcDaoImpl).一般情况下都是用户自己实现。
     *  实现通常是在现有的数据访问对象（DAO）的封装，代表员工，客户或应用程序的其他用户.
     *  请记住，无论您的UserDetailsService返回什么，都可以从security上下文中获得。
     * */
    private void getUserName(){
        //SecurityContext 是线程安全的（默认情况，可根据需求修改）。Spring Security中的大多数身份验证机制都将UserDetails的一个实例作为主体返回
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //获取用户信息
        String userName;
        Object principal = authentication.getPrincipal();
        /*
        大多情况principal都可以转换为UserDetails 对象，UserDetails 是一个核心接口。在某些扩展情况下他代表着principal
        * 将UserDetails看作是是用户数据库的适配器，他必须在Spring security上下文中。*/
        if(principal instanceof UserDetails){
            userName = ((UserDetails) principal).getUsername();
        }else{
            userName = principal.toString();
        }
        System.out.println("current userName is "+ userName);
    }
}
