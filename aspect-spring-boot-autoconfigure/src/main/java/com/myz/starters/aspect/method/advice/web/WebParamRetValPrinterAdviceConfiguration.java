package com.myz.starters.aspect.method.advice.web;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.DispatcherType;

/**
 * @author yzMa
 * @desc
 * @date 2020/7/10 15:48
 * @email 2641007740@qq.com
 */
@Configuration
@ConditionalOnClass(DispatcherServlet.class)
public class WebParamRetValPrinterAdviceConfiguration {

    @Bean
//    @ConditionalOnProperty(value = "myz.aspect.printer.web.enabled",havingValue = "true")
    public WebParamRetValPrinterAdvice webParamRetValPrinterAdvice(){
        return new WebParamRetValPrinterAdvice();
    }

    @Bean
    public FilterRegistrationBean<ReadBodyHttpServletFilter> readBodyHttpServletFilterFilterRegistrationBean(){
        FilterRegistrationBean<ReadBodyHttpServletFilter> filterRegistrationBean = new FilterRegistrationBean<>(new ReadBodyHttpServletFilter());
        filterRegistrationBean.setDispatcherTypes(DispatcherType.ASYNC,
                DispatcherType.ERROR, DispatcherType.FORWARD, DispatcherType.INCLUDE,
                DispatcherType.REQUEST);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
}
