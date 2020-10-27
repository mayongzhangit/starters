package com.myz.starters.dynamic.refresh.sample.controller;

import com.myz.starters.dynamic.refresh.sample.event.MyConfigChangeEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yzMa
 * @desc
 * @date 2020/7/17 15:09
 * @email 2641007740@qq.com
 */
@RestController
@RequestMapping("/notify")
public class NotifyChangeController implements ApplicationEventPublisherAware {

    @GetMapping("/{configKey}/changed")
    public String keyChanged(@PathVariable String configKey){
        applicationEventPublisher.publishEvent(new MyConfigChangeEvent(this,configKey,"changedVal"));
        return "notified";
    }

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
