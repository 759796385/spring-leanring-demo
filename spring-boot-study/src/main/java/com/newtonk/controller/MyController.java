package com.newtonk.controller;

import com.google.common.collect.Lists;
import com.newtonk.aspect.sample.Sample;
import com.newtonk.biz.InterfaceHandler;
import com.newtonk.exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/5/23 0023
 */
@RestController
@RequestMapping(value = "/main")
public class MyController {

    private List<InterfaceHandler> interfaceHandlers;

    @Autowired(required = false)
    public void setInterfaceHandlers(List<InterfaceHandler> interfaceHandlers){
        this.interfaceHandlers = interfaceHandlers;
    }

    @GetMapping(value = "/size")
    public int getTest(){
        return interfaceHandlers.size();
    }


    @Autowired
    private Sample<Integer> sample;
    @GetMapping(value = "")
    public Object first(String name,String age){
        return "";
    }

    @GetMapping(value = "/exception")
    public Object throwExcep(){
        throw new MyException("sa");
    }


    @GetMapping(value = "/sample")
    public void sample(){
        sample.sampleGenericCollectionMethod(Lists.newArrayList(1,2,3));
        sample.sampleGenericMethod(21);
    }
}
