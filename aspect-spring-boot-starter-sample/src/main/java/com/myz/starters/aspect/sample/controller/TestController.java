package com.myz.starters.aspect.sample.controller;

import com.myz.starters.aspect.method.annotation.LoggerName;
import com.myz.starters.aspect.method.annotation.ParamRetValPrinter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yzMa
 * @desc
 * @date 2020/10/13 11:07
 * @email 2641007740@qq.com
 */
@LoggerName(topic = "test")
@Slf4j(topic = "test") // slfj @Retention(RetentionPolicy.SOURCE) 运行时获取不到，暂时用一个写法类似的@LoggerName来实现
@RequestMapping("/test")
@RestController
@ParamRetValPrinter
public class TestController {

    @GetMapping("/go")
    public String go(){
        return "go";
    }
}
