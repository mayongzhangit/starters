package com.myz.starters.login.sample.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myz.common.util.ApiResult;
import com.myz.starters.login.annotation.LoginRequired;
import com.myz.starters.login.context.LoginContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yzMa
 * @desc
 * @date 2020/11/4 12:00
 * @email 2641007740@qq.com
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @LoginRequired(required = false)
    @GetMapping("/index")
    public ApiResult index(){
        return ApiResult.OK("index");
    }

    /**
     *
     * @return
     */
    @GetMapping("/get-user")
    public ApiResult getUser(){
        String userJson = LoginContext.get();
        return ApiResult.OK(userJson);
    }
}
