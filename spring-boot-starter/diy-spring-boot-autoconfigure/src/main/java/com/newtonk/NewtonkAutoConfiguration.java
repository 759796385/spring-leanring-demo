package com.newtonk;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 类名称：
 * 类描述：
 * 创建人：newtonk
 * 创建日期：2019/11/17
 */
@Configuration
@ConditionalOnProperty(value = "newtonk.config.enable", havingValue = "true")
@ConditionalOnClass(DiyBean.class)
public class NewtonkAutoConfiguration {
    @ConditionalOnMissingBean
    @Bean
    public NewtonkProperties newtonkProperties() {
        return new NewtonkProperties();
    }

    @Bean
    public DiyBean diyBean(NewtonkProperties newtonkProperties){
        DiyBean bean = new DiyBean();
        bean.setAge(newtonkProperties.age);
        return bean;
    }
}
