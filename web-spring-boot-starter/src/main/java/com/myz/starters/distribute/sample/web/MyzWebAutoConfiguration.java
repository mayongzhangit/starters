package com.myz.starters.distribute.sample.web;

import com.myz.starters.distribute.sample.web.annotation.BodyContentMethodArgumentResolver;
import com.myz.starters.distribute.sample.web.controller.advice.MyzControllerAdviceConfiguration;
import com.myz.starters.distribute.sample.web.exception.handler.BodyContentExceptionHandlerMethodResolver;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.Assert;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.servlet.Servlet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yzMa
 * @desc
 * @date 2020/6/24 10:33
 * @email 2641007740@qq.com
 */
@Configuration
@ConditionalOnClass({ Servlet.class, DispatcherServlet.class, WebMvcConfigurer.class })
@ConditionalOnProperty(prefix = "myz.web",name = "enabled",havingValue = "true",matchIfMissing = true)
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
@Import(MyzControllerAdviceConfiguration.class)
public class MyzWebAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "myz.web",name = "exception-handler.enabled",havingValue = "true",matchIfMissing = true)
    public BodyContentExceptionHandlerMethodResolver bodyContentExceptionHandlerMethodResolver(){
        return new BodyContentExceptionHandlerMethodResolver();
    }

    @Bean
    public BodyContentMethodArgumentResolverPostProcessor bodyContentMethodArgumentResolverPostProcessor(){
        return new BodyContentMethodArgumentResolverPostProcessor();
    }

    static class BodyContentMethodArgumentResolverPostProcessor implements ApplicationContextAware , BeanPostProcessor {

        private ApplicationContext applicationContext;

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.applicationContext = applicationContext;
        }

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            if (!RequestMappingHandlerAdapter.class.isInstance(bean)){
                return bean;
            }
            RequestMappingHandlerAdapter adapter = (RequestMappingHandlerAdapter)bean;
            List<HandlerMethodArgumentResolver> argumentResolvers = adapter.getArgumentResolvers();
            Assert.notNull(argumentResolvers,"argumentResolvers is null");

            List<HandlerMethodArgumentResolver> newResolvers = new ArrayList<>(argumentResolvers.size()+1);
            newResolvers.add(new BodyContentMethodArgumentResolver());
            newResolvers.addAll(argumentResolvers);
            adapter.setArgumentResolvers(newResolvers);// 不是普通的set方法
            return adapter;
        }
    }
}
