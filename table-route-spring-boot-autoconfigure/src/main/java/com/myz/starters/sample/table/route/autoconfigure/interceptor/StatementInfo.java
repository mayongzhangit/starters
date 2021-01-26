package com.myz.starters.sample.table.route.autoconfigure.interceptor;

import com.myz.starters.sample.table.route.autoconfigure.annotation.TableRoutingAnnotation;

import java.lang.reflect.Method;

/**
 * @author yzMa
 * @desc
 * @date 2020/7/6 15:41
 * @email 2641007740@qq.com
 */
public class StatementInfo {
    private Class klass;
    private TableRoutingAnnotation klassTableRoutingAnnotation;
    private Method method;
    private TableRoutingAnnotation methodTableRoutingAnnotation;

    public Class getKlass() {
        return klass;
    }

    public void setKlass(Class klass) {
        this.klass = klass;
    }

    public TableRoutingAnnotation getKlassTableRoutingAnnotation() {
        return klassTableRoutingAnnotation;
    }

    public void setKlassTableRoutingAnnotation(TableRoutingAnnotation klassTableRoutingAnnotation) {
        this.klassTableRoutingAnnotation = klassTableRoutingAnnotation;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public TableRoutingAnnotation getMethodTableRoutingAnnotation() {
        return methodTableRoutingAnnotation;
    }

    public void setMethodTableRoutingAnnotation(TableRoutingAnnotation methodTableRoutingAnnotation) {
        this.methodTableRoutingAnnotation = methodTableRoutingAnnotation;
    }
}
