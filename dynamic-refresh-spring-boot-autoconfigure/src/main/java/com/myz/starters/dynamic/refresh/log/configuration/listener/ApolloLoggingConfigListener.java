package com.myz.starters.dynamic.refresh.log.configuration.listener;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.config.PropertySourcesConstants;
import com.google.common.base.Splitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author yzMa
 * @desc
 * @date 2020/7/23 9:46
 * @email 2641007740@qq.com
 */
public class ApolloLoggingConfigListener implements ApplicationEventPublisherAware , InitializingBean, EnvironmentAware {

    private Logger LOGGER = LoggerFactory.getLogger(ApolloLoggingConfigListener.class);

    private ApplicationEventPublisher applicationEventPublisher;

    private Environment environment;

    private static final Splitter NAMESPACE_SPLITTER = Splitter.on(",").omitEmptyStrings().trimResults();

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String namespaces = environment.getProperty(PropertySourcesConstants.APOLLO_BOOTSTRAP_NAMESPACES);
        if (namespaces == null || namespaces.trim().length() == 0){
            LOGGER.info("current project Apollo namespaces not set");
            return;
        }
        Set<String> interestedKeyPrefixes = new HashSet<>();
        interestedKeyPrefixes.add("logging.level");

        List<String> namespaceList = NAMESPACE_SPLITTER.splitToList(namespaces);
        for (String namespace : namespaceList){
            LOGGER.info("namespace={} add ConfigChangeListener interestedKeyPrefixes={}",namespace,interestedKeyPrefixes);

            Config config = ConfigService.getConfig(namespace);
            config.addChangeListener(new ConfigChangeListener() {
                @Override
                public void onChange(ConfigChangeEvent changeEvent) {
                    LOGGER.info("namespace={} apollo changeKeys={}",namespace,changeEvent.changedKeys());
                    applicationEventPublisher.publishEvent(new EnvironmentChangeEvent(changeEvent.changedKeys()));
                }
            },null,interestedKeyPrefixes);
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
