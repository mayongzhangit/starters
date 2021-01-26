package com.myz.starters.dynamic.refresh.sample.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yzMa
 * @desc
 * @date 2020/12/25 14:24
 * @email 2641007740@qq.com
 */
@Slf4j
@RestController
@RequestMapping("/redis-config-dynamic")
public class RedisConfigDynamicController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/set")
    public void set(){

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("myz","mayongzhan");
    }

    @GetMapping("/get")
    public String get(){

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get("myz");
    }

    @GetMapping("/info")
    public void go(){

        RedisConnectionFactory connectionFactory = redisTemplate.getConnectionFactory();
    }
}
