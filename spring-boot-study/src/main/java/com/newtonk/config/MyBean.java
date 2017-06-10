package com.newtonk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * Created by newtonk on 2017/5/26.
 */
@Component
public class MyBean {

    //创建bean时，可通过自动注入注解来获取其他bean
    @Autowired
    public MyBean(ApplicationArguments args){
        Set<String> argsName = args.getOptionNames();
        System.out.println("容器启动参数是："+argsName);
        List<String> files = args.getNonOptionArgs();
        // if run with "--debug logfile.txt" debug=true, files=["logfile.txt"]
    }
}
