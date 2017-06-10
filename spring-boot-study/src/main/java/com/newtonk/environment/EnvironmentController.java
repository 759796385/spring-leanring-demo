package com.newtonk.environment;

import com.newtonk.environment.bean.EnvironmentBean;
import com.newtonk.environment.bean.PropertyBean;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by newtonk on 2017/5/29.
 */
@RestController
@RequestMapping(value = "/env")
public class EnvironmentController implements ApplicationContextAware{



    private ApplicationContext applicationContext;

    @GetMapping(value = "/profile")
    public String get(){
        return applicationContext.getBean(EnvironmentBean.class).getName();
    }

    @GetMapping(value = "/property")
    public String getProperty(){
        return applicationContext.getBean(PropertyBean.class).getName();
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext= applicationContext;
    }
}
