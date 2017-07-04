package com.newtonk;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * 类名称： 获取服务
 * 类描述：
 * 创建人：tq
 * 创建日期：
 */
@SpringBootApplication
public class ServerClientApplication {


    public static void main(String[] args) {
        new SpringApplicationBuilder(ServerClientApplication.class).web(true).run(args);
    }
}
