package com.newtonk.redislock;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类名称：
 * 类描述：
 * 创建人：tq
 * 创建日期：2017/11/9 0009
 */
@RestController
@RequestMapping(value = "/redis")
public class RedisController {
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping(value = "")
    public void test(){

    }
}
