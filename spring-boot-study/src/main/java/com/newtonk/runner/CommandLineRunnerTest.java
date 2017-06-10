package com.newtonk.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by newtonk on 2017/5/26.
 */
@Slf4j
@Component
//可通过Order注解来确定启动顺序，也可实现Ordered接口来确定启动顺序
@Order(4)
public class CommandLineRunnerTest implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("通过CommandLineRunner接口获取 -- 参数就是这些货" + args.length);
    }
}
