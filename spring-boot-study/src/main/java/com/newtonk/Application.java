package com.newtonk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/5/23 0023
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) throws Exception {
        //
        args = new String[]{"debug","aaa"};
        SpringApplication app = new SpringApplication(Application.class);
        ConfigurableApplicationContext context = app.run(args);
        //可获取environment
        System.out.println("当前环境加载的profile就是：" + Arrays.toString(context.getEnvironment().getActiveProfiles()));

    }



}
