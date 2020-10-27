package com.myz.starters.dynamic.refresh.sample.listener;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * @author yzMa
 * @desc
 * @date 2020/7/23 9:46
 * @email 2641007740@qq.com
 */
@Component
public class ApolloLogConfigListener implements ApplicationEventPublisherAware {

    private Logger LOGGER = LoggerFactory.getLogger(ApolloLogConfigListener.class);

    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * 处理的namespace=application，做成通用的就不行
     * @param configChangeEvent
     */
    @ApolloConfigChangeListener(interestedKeyPrefixes = "logging.level")
    public void onLogConfigChanged(ConfigChangeEvent configChangeEvent){
        LOGGER.info("configEvent={}",configChangeEvent);
        applicationEventPublisher.publishEvent(new EnvironmentChangeEvent(configChangeEvent.changedKeys()));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
