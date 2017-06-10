package com.newtonk.externalizedConfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by newtonk on 2017/6/5.
 */
@Configuration
@PropertySource("classpath:config/my.properties")
//@EnableConfigurationProperties(MyProperties.class)
public class PropertiesConfiguer {
    @Bean
    @ConfigurationProperties(prefix = "my")
    public MyProperties regist(){
        MyProperties s = new MyProperties();
        s.setAge(88);//即使被设值也会被覆盖
        return  s;
    }
}
