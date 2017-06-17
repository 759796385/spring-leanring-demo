package com.newtonk.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.newtonk.DO.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by newtonk on 2017/6/17.
 */
@RestController
public class UserController {

    /**
     * 声明JsonView，返回对象的属性只有被@JsonView(User.WithoutPasswordView.class)注解的才会被序列化
     * 其实就是属性过滤
     */
    @GetMapping("/user")
    @JsonView(User.WithoutPasswordView.class)
    public User getUser() {
        return new User("eric", "7!jd#h23");
    }
}