package com.newtonk.config;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类名称：全局数据转换
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/6/16 0016
 */
public class MyBindingInitalizer implements WebBindingInitializer {
    @Override
    public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        webDataBinder.registerCustomEditor(Date.class,new CustomDateEditor(dateFormat, false));
    }
}
