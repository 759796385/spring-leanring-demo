package com.newtonk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 类名称：
 * 类描述：
 * 创建人：newtonk
 * 创建日期：2019/11/17
 */
@SpringBootApplication
public class Test {
    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(Test.class);
        ConfigurableApplicationContext context = app.run(args);

        DiyBean diyBean = context.getBean(DiyBean.class);
        diyBean.dosome();
    }
}
