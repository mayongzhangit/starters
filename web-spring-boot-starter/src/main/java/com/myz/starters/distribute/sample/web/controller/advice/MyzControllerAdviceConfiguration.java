package com.myz.starters.distribute.sample.web.controller.advice;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author yzMa
 * @desc
 * @date 2020/10/13 15:45
 * @email 2641007740@qq.com
 */
@Configuration
@ConditionalOnProperty(prefix = "myz.controller.advice",name = "enabled",havingValue = "true",matchIfMissing = true)
@Import(MyzControllerAdvice.class)
public class MyzControllerAdviceConfiguration {

}
