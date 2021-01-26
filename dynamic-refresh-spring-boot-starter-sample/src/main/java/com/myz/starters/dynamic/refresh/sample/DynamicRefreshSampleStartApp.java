package com.myz.starters.dynamic.refresh.sample;

import com.ctrip.framework.apollo.core.ConfigConsts;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

/**
 * @author yzMa
 * @desc @see {@link WebMvcAutoConfiguration.EnableWebMvcConfiguration}
 * @date 2020/7/17 12:52
 * @email 2641007740@qq.com
 */
@Slf4j
@ServletComponentScan("com.myz.starters.dynamic.refresh.sample.servlet")
@SpringBootApplication
public class DynamicRefreshSampleStartApp implements ApplicationEventPublisherAware {

    public static void main(String[] args) {
        SpringApplication.run(DynamicRefreshSampleStartApp.class,args);
    }

//    static class WebConfig implements WebMvcConfigurer{
//        @Override
//        public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//            System.out.println(converters);
//            for (HttpMessageConverter httpMessageConverter : converters){
//                boolean equals = httpMessageConverter.getClass().getSimpleName().equals(StringHttpMessageConverter.class.getSimpleName());
//                if (equals){ //解决乱码问题
//                    StringHttpMessageConverter stringHttpMessageConverter = (StringHttpMessageConverter)httpMessageConverter;
//                    stringHttpMessageConverter.setDefaultCharset(StandardCharsets.UTF_8);
//                }
//            }
//        }
//    }

    @Autowired
    private ContextRefresher contextRefresher;

    @ApolloConfigChangeListener(value = ConfigConsts.NAMESPACE_APPLICATION,interestedKeyPrefixes = "spring.redis")
    public void onLogConfigChanged(ConfigChangeEvent configChangeEvent) throws JsonProcessingException {
        log.info("namespace={} apollo changeKeys={}",configChangeEvent.getNamespace(),configChangeEvent.changedKeys());
//        applicationEventPublisher.publishEvent(new EnvironmentChangeEvent(configChangeEvent.changedKeys()));
        // refresh Scope会先调用publishEvent
        contextRefresher.refresh();


    }

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
