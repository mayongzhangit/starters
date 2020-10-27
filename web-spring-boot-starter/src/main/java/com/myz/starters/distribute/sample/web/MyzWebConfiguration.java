package com.myz.starters.distribute.sample.web;

import com.myz.starters.distribute.sample.web.annotation.BodyContentMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.List;

/**
 * @author yzMa
 * @desc
 * @date 2020/6/24 14:32
 * @email 2641007740@qq.com
 */
//@Configuration
public class MyzWebConfiguration implements WebMvcConfigurer {

    /**
     * s：目前
     * {@link RequestMappingHandlerAdapter}#afterPropertiesSet#getDefaultArgumentResolvers
     * argumentResolvers为list结构，切优先加入了默认的一些常见的HandlerMethodArgumentResolver
     * 这里使用WebMvcConfigurer#addArgumentResolvers为customArgumentResolvers
     * 追加到了list的末尾，因此无法改变顺序
     *
     * c：目前Controller的方法参数，要校验s参数的跟@RequestBody和Map存在，因此会被MapMethodProcessor和RequestResponseBodyMethodProcessor解析，
     * q：如何修改顺序
     * a：自然是修改RequestMappingHandlerAdapter的成员的值，采用BeanPostProcessor方式
     * @param resolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new BodyContentMethodArgumentResolver());
    }
}
