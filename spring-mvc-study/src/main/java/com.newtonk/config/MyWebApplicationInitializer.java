package com.newtonk.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/6/12 0012
 */
public class MyWebApplicationInitializer implements WebApplicationInitializer {
    /**
     * Configure the given {@link ServletContext} with any servlets, filters, listeners
     * context-params and attributes necessary for initializing this web application. See
     * examples {@linkplain WebApplicationInitializer above}.
     *
     * @param servletContext the {@code ServletContext} to initialize
     * @throws ServletException if any call against the given {@code ServletContext}
     *                          throws a {@code ServletException}
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
            ServletRegistration.Dynamic registration = servletContext.addServlet("example", new DispatcherServlet());
            registration.setLoadOnStartup(1);
            registration.addMapping("/example/*");
    }
}
