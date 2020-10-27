package com.myz.starters.distribute.sample.sample.controller;

import com.myz.starters.distribute.sample.web.annotation.BodyContentMethodArgumentResolver;
import com.myz.starters.distribute.sample.web.exception.annotation.BodyContentExceptionAdvice;
import com.myz.starters.distribute.sample.web.annotation.BodyContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author yzMa
 * @desc
 * @date 2020/6/28 10:21
 * @email 2641007740@qq.com
 */
@Slf4j
@RestController
@RequestMapping("/body-content")
@BodyContentExceptionAdvice
public class BodyContentController {

    /**
     * 测试方法参数解析器 {@link BodyContentMethodArgumentResolver}
     * @param parsedMap
     * @return
     */
    @PostMapping("/login-by-open-sgp")
    public String loginByOpenSGP(@RequestBody @BodyContent Map<String, String> parsedMap) {
        log.info("parsedMap={}",parsedMap);
        return "success";
    }
}