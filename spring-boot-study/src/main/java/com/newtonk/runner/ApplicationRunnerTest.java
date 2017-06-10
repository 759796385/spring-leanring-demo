package com.newtonk.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * Created by newtonk on 2017/5/26.
 */
@Component
//可通过Order注解来确定启动顺序，也可实现Ordered接口来确定启动顺序
@Order(5)
public class ApplicationRunnerTest implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("参数" + args.getNonOptionArgs());
    }



}
