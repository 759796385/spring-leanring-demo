package com.newtonk.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/6/16 0016
 */
@RestController
@RequestMapping("/model")
public class ModelAttributeController {

    @ModelAttribute
    public void populateModel(@RequestParam String abc, Model model) {
        model.addAttribute("attributeName", abc);
    }

    @RequestMapping(value = "/helloWorld")
    public String helloWorld() {
        return "index";
    }
}
