package com.myz.starters.aspect.method.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yzMa
 * @desc
 * @date 2020/7/7 19:39
 * @email 2641007740@qq.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface ParamRetValPrinter {

}
