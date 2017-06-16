package com.newtonk.aspect;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类名称：restController层增强切面
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/6/16 0016
 */
//支持注解，包，类
@RestControllerAdvice(annotations = RestController.class)
//@RestControllerAdvice("com.newtonk.controller.DataBinderController")
//@ControllerAdvice(assignableTypes = {ControllerInterface.class, AbstractController.class})
public class RestControllerAdviceConfig {

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        System.out.println("sadsa");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        webDataBinder.registerCustomEditor(Date.class,new CustomDateEditor(dateFormat, false));
//        webDataBinder.addCustomFormatter(new DateFormatter("yyyy-MM-dd"));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String myError(Exception exception) {
        return exception.getMessage();
    }
}
