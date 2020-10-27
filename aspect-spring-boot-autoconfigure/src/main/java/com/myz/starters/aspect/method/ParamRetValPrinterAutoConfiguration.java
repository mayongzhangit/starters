package com.myz.starters.aspect.method;

import com.myz.starters.aspect.method.advice.ParamRetValPrinterAdvice;
import com.myz.starters.aspect.method.advice.web.WebParamRetValPrinterAdviceConfiguration;
import com.myz.starters.aspect.method.advisor.ParamRetValPrinterPointcutAdvisor;
import com.myz.starters.aspect.method.annotation.EnableParamRetValPrinter;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.*;

/**
 * @author yzMa
 * @desc
 * @date 2020/7/7 19:31
 * @email 2641007740@qq.com
 */
@Configuration
@ConditionalOnProperty(value = "myz.aspect.printer.web.enabled",havingValue = "false",matchIfMissing = true)
@Import(WebParamRetValPrinterAdviceConfiguration.class)
@EnableParamRetValPrinter(mode = AdviceMode.PROXY)
public class ParamRetValPrinterAutoConfiguration {

    @Bean
    @ConditionalOnMissingClass({"org.springframework.web.servlet.DispatcherServlet"})
    public ParamRetValPrinterAdvice paramRetValPrinterAdvice(){
        return new ParamRetValPrinterAdvice();
    }

    @Role(BeanDefinition.ROLE_INFRASTRUCTURE) // 标识后台角色，与最终用户无关，供内部工作的bean使用
    @Bean
    public ParamRetValPrinterPointcutAdvisor paramRetValPrinterPointcutAdvisor(ParamRetValPrinterAdvice advice){
        return new ParamRetValPrinterPointcutAdvisor(advice);
    }
}
