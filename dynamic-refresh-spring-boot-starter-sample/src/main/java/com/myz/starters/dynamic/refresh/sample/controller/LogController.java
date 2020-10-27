package com.myz.starters.dynamic.refresh.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yzMa
 * @desc
 * @date 2020/7/22 16:37
 * @email 2641007740@qq.com
 */
@RestController
@RequestMapping("/log")
public class LogController {

    private Logger LOGGER = LoggerFactory.getLogger(LogController.class);

    @Autowired
    private LoggingSystem loggingSystem;

    @GetMapping("{name}/2/{level}")
    public String changeLogLevel(@PathVariable String name,@PathVariable String level){
        loggingSystem.setLogLevel(name,LogLevel.valueOf(level.toUpperCase()));
//        loggingSystem.getLoggerConfiguration()
        return "success";
    }

    @GetMapping("/print")
    public String print(){
        LOGGER.trace("trace print");
        LOGGER.debug("debug print");
        LOGGER.info("info print");
        LOGGER.error("error print");
        return "done";
    }
}
