package com.myz.starters.distribute.sample.web.controller.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yzMa
 * @desc
 * @date 2020/10/13 16:00
 * @email 2641007740@qq.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MyzControllerAdviceAnno {
}

