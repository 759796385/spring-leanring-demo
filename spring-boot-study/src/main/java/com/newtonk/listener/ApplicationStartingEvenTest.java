package com.newtonk.listener;

import org.springframework.boot.context.event.*;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/5/24 0024
 */
public class ApplicationStartingEvenTest implements ApplicationListener {
    /**
     * 注册监听器需要在classPath下创建META-INF/spring.factories文件
     *  或者可以使用SpringApplication.addListeners(…​) or SpringApplicationBuilder.listeners(…​）---- 没成功过
     */
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if(applicationEvent instanceof ApplicationStartingEvent){
            System.out.println("在运行开始，但除了监听器注册和初始化以外的任何处理之前，会发送一个ApplicationStartedEvent。");
        }  if(applicationEvent instanceof ApplicationEnvironmentPreparedEvent){
            System.out.println("在Environment将被用于已知的上下文，但在上下文被创建前，会发送一个ApplicationEnvironmentPreparedEvent");
        }  if(applicationEvent instanceof ApplicationPreparedEvent){
            System.out.println("在refresh开始前，但在bean定义已被加载后，会发送一个ApplicationPreparedEvent");
        } if(applicationEvent instanceof ApplicationReadyEvent  ){
            System.out.println("在refresh之后，相关的回调处理完，会发送一个ApplicationReadyEvent，表示应用准备好接收请求了。");
        } if(applicationEvent instanceof ApplicationFailedEvent){
            System.out.println("容器启动失败");
        }
    }
}
