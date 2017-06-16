package com.newtonk.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/6/16 0016
 */
@Controller
@RequestMapping(value = "/data")
public class DataBinderController {

    /**
     * 数据转换，作用于当前Controller中。必须是void方法
     * WebDataBinder可注册CustomDateEditor 。也可使用PropertyEditor 实例来进行转换
     * 通常建议放到全局中去注册
     */
//    @InitBinder
//    public void initBinder(WebDataBinder webDataBinder){
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
////        webDataBinder.registerCustomEditor(Date.class,new CustomDateEditor(dateFormat, false));
//        webDataBinder.addCustomFormatter(new DateFormatter("yyyy-MM-dd"));
//    }

    @GetMapping("/date")
    public String testDate(@RequestParam Date date){
        System.out.println(date.getTime()+"");
        return "index";
    }
}
