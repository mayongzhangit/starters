package com.myz.starters.dynamic.refresh.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * @author yzMa
 * @desc
 * @date 2020/7/22 18:37
 * @email 2641007740@qq.com
 */

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/go")
    public String go(){
        String result =restTemplate.postForEntity("http://localhost:8080/test/server", new HashMap<>(), String.class).getBody();
        return result;
    }

    @PostMapping("/server")
    public String server(){
        return "{\"code\":404,\"error\":{\"message\":\"item Not Found\"}}";
    }
}
