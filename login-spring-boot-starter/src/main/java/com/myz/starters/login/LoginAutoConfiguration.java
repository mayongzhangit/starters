package com.myz.starters.login;

import com.myz.starters.login.interceptor.LoginHandlerInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author yzMa
 * @desc
 * @date 2020/7/13 15:48
 * @email 2641007740@qq.com
 */
@Configuration
@ConditionalOnProperty(prefix = "login.interceptor", name = "enabled", havingValue = "true", matchIfMissing = true)
public class LoginAutoConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // TODO 参数可配
        registry.addInterceptor(new LoginHandlerInterceptor())
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
    }
}
