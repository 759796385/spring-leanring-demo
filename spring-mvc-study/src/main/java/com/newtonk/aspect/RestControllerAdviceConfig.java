package com.newtonk.aspect;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 类名称：restController层增强切面,注意，@RestControllerAdvice注解需要被ComponentScan扫描到，否则不起作用
 * 类描述： 注意@RestController也被@controller注解了
 * 创建人：tq
 * 创建日期：2017/6/16 0016
 */
//支持注解，包，类
@RestControllerAdvice(annotations = RestController.class)
//@RestControllerAdvice("com.newtonk.controller.DataBinderController")
//@ControllerAdvice(assignableTypes = {ControllerInterface.class, AbstractController.class})
public class RestControllerAdviceConfig {

//    @ModelAttribute
    public Map newUser() {
        System.out.println("============应用到RestController方法，在其执行之前把返回值放入Model");
        Map<String,Object> map = new HashMap<>();
        map.put("userId",456);
        return map;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        System.out.println("== 执行rest控制器方法前先调用我 ==");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        webDataBinder.registerCustomEditor(Date.class,new CustomDateEditor(dateFormat, false));
//        webDataBinder.addCustomFormatter(new DateFormatter("yyyy-MM-dd"));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String myError(Exception exception) {
        System.out.println("捕捉restContrller的异常");
        System.out.println(exception.toString());
        return exception.getMessage();
    }
}
