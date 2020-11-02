package com.myz.starters.notice.sample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yzMa
 * @desc
 * @date 2020/10/30 18:03
 * @email 2641007740@qq.com
 */

@RestController
@RequestMapping("/")
public class OkController {

    @GetMapping("/ok")
    public String ok(){
        return "ok";
    }
}
