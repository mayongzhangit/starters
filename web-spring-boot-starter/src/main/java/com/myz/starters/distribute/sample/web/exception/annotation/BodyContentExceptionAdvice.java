package com.myz.starters.distribute.sample.web.exception.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yzMa
 * @desc
 * @date 2020/6/28 12:01
 * @email 2641007740@qq.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface BodyContentExceptionAdvice {

}
