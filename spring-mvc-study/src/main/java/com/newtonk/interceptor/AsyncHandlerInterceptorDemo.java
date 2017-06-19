package com.newtonk.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 类名称：AsyncHandlerInterceptor是HandlerInterceptor子类
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/6/18 0018
 */
@Component
public class AsyncHandlerInterceptorDemo implements AsyncHandlerInterceptor {
    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        System.out.println("进行异步处理的时候,调用此");
    }

    /**
     *   方法返回一个boolean值.你可以使用这个方法来打断或者继续处理的执行链.
     *   当这个方法返回true的时候,这个handler的执行链将会继续执行;
     *   当这个这个方法返回值为false时,DispatcherServlet会假设拦截器已经处理了request
     *   (例如:渲染一个合适的页面).并且不会继续执行其它的拦截器和执行链当中真实的handler
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("handler被执行之后会调用此方法. ");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("当完整的request完全之后会调用此方法. ");
    }
}
