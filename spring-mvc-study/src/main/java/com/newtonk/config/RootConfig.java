package com.newtonk.config;


import com.newtonk.filter.MyFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/6/15 0015
 */
@Configuration
public class RootConfig {

    @Bean
    public MyFilter myFilter(){
        return new MyFilter();
    }

    @Bean
    public DemoBean registerBean(){
        DemoBean demoBean = new DemoBean();
        demoBean.setAge(23);
        demoBean.setName("newtonk");
        return demoBean;
    }
}
