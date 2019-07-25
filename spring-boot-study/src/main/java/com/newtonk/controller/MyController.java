package com.newtonk.controller;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.common.collect.Lists;
import com.newtonk.aspect.sample.Sample;
import com.newtonk.biz.InterfaceHandler;
import com.newtonk.exception.MyException;
import com.newtonk.externalizedConfig.MyProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping(value = "/pro")
    public MyProperties properties(){
        return new MyProperties();
    }

    public static void main(String[] args) throws IOException {
        //  解析
        ObjectMapper xmlMapper = new XmlMapper();

        MyProperties myProperties = new MyProperties();
        myProperties.setAge(12);
        myProperties.setEnable(true);
        myProperties.setName("jie");
        System.out.println(xmlMapper.writeValueAsString(myProperties));

        //
        Simple value = xmlMapper.readValue("<Simple><x>1</x><y>2</y></Simple>", Simple.class);
        System.out.println(value.x);
    }



}
class Simple {
    public int x = 1;
    public int y = 2;
}
