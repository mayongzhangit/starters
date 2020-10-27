package com.myz.starters.aspect.method.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yzMa
 * @desc 因为获取不到@Slfj注解的topic，@Slfj是source阶段生效
 * @date 2020/10/13 10:35
 * @email 2641007740@qq.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface LoggerName {

    String topic();
}