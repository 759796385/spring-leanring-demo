package com.newtonk.aspect;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 类名称：注意，@RestControllerAdvice注解需要被ComponentScan扫描到，否则不起作用
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/6/16 0016
 */
//@ControllerAdvice(annotations = Controller.class)
    @Deprecated
public class ControllerAdviceConfig {

    @ModelAttribute
    public Map newUser() {
        System.out.println("============应用到Controller的方法，在其执行之前把返回值放入Model");
        Map<String,Object> map = new HashMap<>();
        map.put("userId",123);
        return map;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        System.out.println("============应用到Controller方法，在其执行之前初始化数据绑定器");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        webDataBinder.registerCustomEditor(Date.class,new CustomDateEditor(dateFormat, false));
//        webDataBinder.addCustomFormatter(new DateFormatter("yyyy-MM-dd"));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String processUnauthenticatedException(NativeWebRequest request, IllegalArgumentException e) {
        System.out.println("===========应用到Controller的方法，在其抛出IllegalArgumentException异常时执行");
        return "index"; //返回一个逻辑视图名
    }
}
