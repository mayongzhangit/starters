package com.myz.starters.notice.dingding;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.Set;

/**
 * @author yzMa
 * @desc
 * @date 2020/10/30 17:28
 * @email 2641007740@qq.com
 */
@Configuration
@ConditionalOnProperty(prefix = "game.notice.dingding",name = "enabled",havingValue = "true",matchIfMissing = true)
@EnableConfigurationProperties(DingDingProperties.class)
public class DingDingNoticeConfiguration implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private Environment environment;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        DingDingProperties dingDingProperties = Binder.get(environment).bind("game.notice.dingding",DingDingProperties.class).get();

        Map<String, DingDingInfo> dingInfoMap = dingDingProperties.getInfoMap();
        if (CollectionUtils.isEmpty(dingInfoMap)){

            return;
        }
        Set<Map.Entry<String, DingDingInfo>> entries = dingInfoMap.entrySet();
        for (Map.Entry<String, DingDingInfo> entry : entries) {
            String beanName = entry.getKey();
            DingDingInfo dingDingInfo = entry.getValue();

            RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(DingDingService.class, () -> new DingDingService(dingDingInfo));
            if (dingDingProperties.getPrimaryBeanName().equals(beanName)){
                rootBeanDefinition.setPrimary(true);
            }

            registry.registerBeanDefinition(beanName,rootBeanDefinition);
        }

    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
