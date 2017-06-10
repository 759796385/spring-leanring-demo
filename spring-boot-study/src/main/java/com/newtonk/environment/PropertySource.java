package com.newtonk.environment;

import com.newtonk.environment.bean.PropertyBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Created by newtonk on 2017/5/29.
 */
/* 使用PropertySource来加载app.properties这个属性源 */
@Configuration
@org.springframework.context.annotation.PropertySource("classpath:config/app.properties")
public class PropertySource {
    @Autowired
    Environment environment;

    /* 加载app.properties这个属性源后，从environment中获取属性*/
    @Bean
    public PropertyBean propertyBean(){
        PropertyBean propertyBean = new PropertyBean();
        propertyBean.setName(environment.getProperty("app.name"));
        return propertyBean;
    }
}
