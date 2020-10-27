package com.myz.starters.aspect.method.annotation;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AutoProxyRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yzMa
 * @desc {@link org.springframework.context.annotation.AutoProxyRegistrar }
 * @date 2020/7/8 11:35
 * @email 2641007740@qq.com
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(AutoProxyRegistrar.class)
public @interface EnableParamRetValPrinter {
    /**
     * 尽量要有如下两个属性 {@link AutoProxyRegistrar}
     */

    boolean proxyTargetClass() default true;

    AdviceMode mode() default AdviceMode.PROXY;
}
