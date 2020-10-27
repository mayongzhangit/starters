package com.myz.starters.dynamic.refresh.sample.controller;

import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

/**
 * @author yzMa
 * @desc
 * @date 2020/7/23 10:14
 * @email 2641007740@qq.com
 */
@RestController
@RequestMapping("/event")
public class EventController implements ApplicationEventPublisherAware {

    @GetMapping("/publish")
    public void publish(){
        Set<String> keys = new HashSet<>();
        keys.add("aaa");
        this.applicationEventPublisher.publishEvent(new EnvironmentChangeEvent(keys));
    }

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
