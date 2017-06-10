package com.newtonk.environment;

import com.newtonk.environment.bean.EnvironmentBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by newtonk on 2017/5/29.
 */
@Configuration
@Profile("main")
public class MainEnvironment {

    @Bean
    public EnvironmentBean register(){
        EnvironmentBean bean = new EnvironmentBean();
        bean.setName("main");
        return bean;
    }
}
