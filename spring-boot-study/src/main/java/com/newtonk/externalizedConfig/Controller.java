package com.newtonk.externalizedConfig;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获取environment中的properties
 * Created by newtonk on 2017/5/29.
 */
@RequestMapping(value = "/config")
@RestController
public class Controller {
    @Autowired
    private ValueTest valueTest;
    @Autowired
    private Environment environment;

    @Autowired
    private MyProperties myProperties;
    @GetMapping("/value")
    public String getValue(){
        return valueTest.getName();
    }

    @GetMapping(value = "/value/{env}")
    public String getEnv(@PathVariable String env){
        env = env.replace("=",".");
        return environment.getProperty(env);
    }

    @GetMapping(value = "/my")
    public String getPropertie(){
       return  JSONObject.toJSONString(myProperties);
    }
}
