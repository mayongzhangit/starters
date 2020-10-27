package com.myz.starters.distribute.sample.table.route.autoconfigure.annotation;

import com.myz.starters.distribute.sample.table.route.autoconfigure.route.TableRouter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yzMa
 * @desc
 * @date 2020/6/28 16:46
 * @email 2641007740@qq.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface TableRoutingAnnotation {

    String routeField() default "gUserId"; //

    String suffixName() default "route"; // table_${route}

    Class<? extends TableRouter> tableRouter();
}
