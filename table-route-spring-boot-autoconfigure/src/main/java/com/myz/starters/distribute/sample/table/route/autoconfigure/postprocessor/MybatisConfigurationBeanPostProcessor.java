package com.myz.starters.distribute.sample.table.route.autoconfigure.postprocessor;

import com.myz.starters.distribute.sample.table.route.autoconfigure.interceptor.TableRouteInterceptor;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;


/**
 * @author yzMa
 * @desc
 * @date 2020/6/28 15:49
 * @email 2641007740@qq.com
 */
public class MybatisConfigurationBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!(bean instanceof DefaultSqlSessionFactory)) {
            return bean;
        }
        DefaultSqlSessionFactory defaultSqlSessionFactory = (DefaultSqlSessionFactory)bean;
        Configuration configuration = defaultSqlSessionFactory.getConfiguration();
        configuration.addInterceptor(new TableRouteInterceptor());
        return bean;
    }
}
