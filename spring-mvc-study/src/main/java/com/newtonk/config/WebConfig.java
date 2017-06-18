package com.newtonk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/6/15 0015
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.newtonk")
//@ImportResource(value = {"classpath:spring-mvc.xml"})
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        super.configureAsyncSupport(configurer);
        configurer.setDefaultTimeout(12000);//设置异步请求超时时间 ,超时后报AsyncRequestTimeoutException
        //配置异步请求拦截器地方
//        configurer.registerCallableInterceptors();
//        configurer.registerDeferredResultInterceptors()
        //建议配置线程池 AsyncTaskExecutor ， 默认实现是SimpleAsyncTaskExecutor.
        //可由用户使用同一的线程池管理
        configurer.setTaskExecutor(new ConcurrentTaskExecutor());
    }

    @Bean
    public ViewResolver viewResolver(){
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/view/");
        resolver.setSuffix(".jsp");
        resolver.setExposeContextBeansAsAttributes(true);
        return resolver;
    }

    /**
     * 对静态资源的访问请求转发到servlet容器的默认servlet上
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        super.configureDefaultServletHandling(configurer);
        configurer.enable();
    }


}
