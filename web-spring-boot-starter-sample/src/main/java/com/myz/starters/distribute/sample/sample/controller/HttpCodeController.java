package com.myz.starters.distribute.sample.sample.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yzMa
 * @desc
 * @date 2020/6/28 14:37
 * @email 2641007740@qq.com
 */
@RestController
@RequestMapping("/http-code")
public class HttpCodeController {

    @GetMapping("/400")
    public HttpStatus code400(){
        return HttpStatus.BAD_REQUEST;
    }
}
