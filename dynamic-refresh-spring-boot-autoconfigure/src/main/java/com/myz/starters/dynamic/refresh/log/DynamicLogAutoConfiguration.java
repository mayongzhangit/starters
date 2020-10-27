package com.myz.starters.dynamic.refresh.log;

import com.myz.starters.dynamic.refresh.log.configuration.LoggingConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author yzMa
 * @desc
 * @date 2020/7/17 11:22
 * @email 2641007740@qq.com
 */
@Configuration
@Import(LoggingConfiguration.class)
public class DynamicLogAutoConfiguration {
}
