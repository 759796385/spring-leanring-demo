package com.newtonk.controller;

import com.newtonk.DO.User;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/6/28 0028
 */
@RestController
@RequestMapping(value = "/cache")
public class CacheController {

    @GetMapping("/{user_id}")
    public ResponseEntity<User> showUser(@PathVariable(value = "user_id") String userId){
        User user = new User();
        user.setUsername(userId);
        //返回值能直接设置Etag和Cache contrllor。
        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES)).eTag(""+user.hashCode()).body(user);
    }
}
