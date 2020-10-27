package com.myz.starters.dynamic.refresh.log.configuration;

import com.ctrip.framework.apollo.ConfigService;
import com.myz.starters.dynamic.refresh.log.configuration.listener.ApolloLoggingConfigListener;
import com.myz.starters.dynamic.refresh.log.configuration.listener.SimpleApolloLoggingConfigListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.logging.LoggingRebinder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author yzMa
 * @desc
 * @date 2020/7/23 15:05
 * @email 2641007740@qq.com
 */
@Configuration
@ConditionalOnClass({ConfigService.class, LoggingRebinder.class})
@ConditionalOnProperty(value = "dynamic.logging.refresh.apollo.enabled",havingValue = "true",matchIfMissing = true)
@Import({ApolloLoggingConfigListener.class,SimpleApolloLoggingConfigListener.class})
public class LoggingConfiguration {

}
