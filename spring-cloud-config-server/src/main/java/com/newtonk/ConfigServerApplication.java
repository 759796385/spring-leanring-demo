package com.newtonk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * 可通过http://localhost:8088/microservice/dev/master或者http://localhost:8088/microservice/dev/develop访问属性
 *
 * {application}--{profile}.properties 文件命名规则
 * 访问规则：
 *   /{application}/{profile}[/{label}]   label即分支，默认master
 *  [/{label}] /{application}/{profile}.yml
 *   [/{label}]/{application}/{profile}.properties  分支都可不传
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class,args);
    }
}
