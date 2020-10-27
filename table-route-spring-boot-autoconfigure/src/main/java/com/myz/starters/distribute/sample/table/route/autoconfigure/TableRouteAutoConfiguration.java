package com.myz.starters.distribute.sample.table.route.autoconfigure;

import com.myz.starters.distribute.sample.table.route.autoconfigure.postprocessor.MybatisConfigurationBeanPostProcessor;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yzMa
 * @desc
 * @date 2020/6/28 15:46
 * @email 2641007740@qq.com
 */
@Configuration
@ConditionalOnClass({org.apache.ibatis.session.Configuration.class, Interceptor.class})
public class TableRouteAutoConfiguration {

    @Bean
    public MybatisConfigurationBeanPostProcessor mybatisConfigurationBeanPostProcessor(){
        return new MybatisConfigurationBeanPostProcessor();
    }
}
