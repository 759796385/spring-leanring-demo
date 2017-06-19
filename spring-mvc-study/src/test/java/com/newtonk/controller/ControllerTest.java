package com.newtonk.controller;

import com.newtonk.config.DemoBean;
import com.newtonk.config.MyMvcControllerTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by newtonk on 2017/6/19.
 */
public class ControllerTest extends MyMvcControllerTest {
    @Autowired
    private DemoBean demoBean;

    @Test
    public  void test(){
        Assert.assertEquals(demoBean.getAge(),23);
        Assert.assertEquals(demoBean.getName(),"newtonk");
    }
}
