package com.myz.starters.dynamic.refresh.log.configuration.listener;

import com.ctrip.framework.apollo.core.ConfigConsts;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloAnnotationProcessor;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

/**
 * @author yzMa
 * @desc
 * @date 2020/7/23 19:05
 * @email 2641007740@qq.com
 */
public class SimpleApolloLoggingConfigListener implements ApplicationEventPublisherAware {

    private Logger LOGGER = LoggerFactory.getLogger(ApolloLoggingConfigListener.class);

    private ApplicationEventPublisher applicationEventPublisher;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * 简单，不通用
     * 只能处理默认的 namespace= application 即 {@link ConfigConsts}.NAMESPACE_APPLICATION
     * @see {@link ApolloAnnotationProcessor}#processMethod
     * @param configChangeEvent
     * @throws JsonProcessingException
     */
    @ApolloConfigChangeListener(value = ConfigConsts.NAMESPACE_APPLICATION,interestedKeyPrefixes = "logging.level")
    public void onLogConfigChanged(ConfigChangeEvent configChangeEvent) throws JsonProcessingException {
        LOGGER.info("namespace={} apollo changeKeys={}",configChangeEvent.getNamespace(),configChangeEvent.changedKeys());
        applicationEventPublisher.publishEvent(new EnvironmentChangeEvent(configChangeEvent.changedKeys()));
    }

}
