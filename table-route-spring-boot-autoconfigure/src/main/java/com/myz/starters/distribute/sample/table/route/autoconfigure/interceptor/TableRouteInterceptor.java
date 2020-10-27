package com.myz.starters.distribute.sample.table.route.autoconfigure.interceptor;

import com.myz.starters.distribute.sample.table.route.autoconfigure.annotation.TableRoutingAnnotation;
import com.myz.starters.distribute.sample.table.route.autoconfigure.route.TableRouter;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.ParamNameResolver;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yzMa
 * @desc
 * @date 2020/6/28 15:44
 * @email 2641007740@qq.com
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class TableRouteInterceptor implements Interceptor {

    private Logger LOGGER = LoggerFactory.getLogger(TableRouteInterceptor.class);

    private Map<String,StatementInfo> methodMap = new ConcurrentHashMap<>();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement ms = (MappedStatement)invocation.getArgs()[0];
        String msId = ms.getId();

        StatementInfo targetStatementInfo = methodMap.computeIfAbsent(msId, key -> {
            try {
                int lastIndexOf = key.lastIndexOf(".");
                String mapperClassName = key.substring(0, lastIndexOf);
                String methodName = key.substring(lastIndexOf + 1);

                Class<?> mapperClass = Class.forName(mapperClassName);
                Method statementMethod = getMethodByName(mapperClass, methodName);
                Assert.notNull(statementMethod,methodName+"在"+mapperClass.getName()+"中不存在");

                StatementInfo statementInfo = new StatementInfo();
                statementInfo.setKlass(mapperClass);
                statementInfo.setMethod(statementMethod);
                statementInfo.setKlassTableRoutingAnnotation(mapperClass.getAnnotation(TableRoutingAnnotation.class));
                statementInfo.setMethodTableRoutingAnnotation(statementMethod.getAnnotation(TableRoutingAnnotation.class));
                return statementInfo;
            } catch (ClassNotFoundException  e) {
                e.printStackTrace(); // 只要类加载器没有问题这里就没有问题
            }
            return null;
        });

        Assert.notNull(targetStatementInfo,msId+"未找到对应的targetStatementInfo");
        TableRoutingAnnotation methodTableRoutingAnnotation = targetStatementInfo.getMethodTableRoutingAnnotation();
        TableRoutingAnnotation klassTableRoutingAnnotation = targetStatementInfo.getKlassTableRoutingAnnotation();
        TableRoutingAnnotation tableRoutingAnnotation = null;
        if (methodTableRoutingAnnotation!=null){
            tableRoutingAnnotation = methodTableRoutingAnnotation;
        }else if (klassTableRoutingAnnotation!=null){
            tableRoutingAnnotation = klassTableRoutingAnnotation;
        }else {
            return invocation.proceed();
        }

        Object paramObject = invocation.getArgs()[1];
        if(paramObject == null){
            return invocation.proceed();
        }

        String routeField = tableRoutingAnnotation.routeField();
        String suffixName = tableRoutingAnnotation.suffixName();
        Class<? extends TableRouter> tableRouterClass = tableRoutingAnnotation.tableRouter();
        TableRouter tableRouter = tableRouterClass.newInstance();
        LOGGER.info("tableRouter={},msId={}",tableRouter,msId);
        /**
         *  {@link org.apache.ibatis.reflection.ParamNameResolver}#getNamedParams 将反射获取到的 Object[] args 转换成了Object param的过程
         *  不是Map以为这这里只有一个字段，即JavaBean或者基本类型（或包装类） 日期之类的排除掉
         */
        if(paramObject instanceof Map){
            Map<String,Object> rebuiltMap = (Map<String,Object>)paramObject;
            String routingKeyValue = (String) rebuiltMap.get(routeField);
            if(routingKeyValue == null || routingKeyValue.length() == 0){
                LOGGER.info("routeField={} not found in map",routeField);
                return invocation.proceed();
            }
            String routeSuffixVal = tableRouter.route(routingKeyValue);
            rebuiltMap.put(suffixName,routeSuffixVal);
            LOGGER.info("paramObject[Map] add route related k,v pair rebuiltMap={}",rebuiltMap);
            return invocation.proceed();
        }

        ParamNameResolver paramNameResolver = new ParamNameResolver(ms.getConfiguration(), targetStatementInfo.getMethod());
        String theOnlyOneParamName = paramNameResolver.getNames()[0];
        Map<String,Object> rebuiltMap = new HashMap<>();
        rebuiltMap.put(theOnlyOneParamName,paramObject);
        rebuiltMap.put(routeField,paramObject); // 做一下兼容，方法名和@TableRoutingAnnotation指定的可以在 #{name}

        boolean hasTypeHandler = ms.getConfiguration().getTypeHandlerRegistry().hasTypeHandler(paramObject.getClass());
        if (hasTypeHandler){
            rebuiltMap.put(suffixName,tableRouter.route(paramObject+""));
            LOGGER.info("paramObject[white list in TypeHandlerRegistry] -> rebuiltMap={}",rebuiltMap);

            invocation.getArgs()[1] = rebuiltMap;
            return invocation.proceed();
        }
        try {
            PropertyDescriptor pd = new PropertyDescriptor(routeField,paramObject.getClass());
            Method readMethod = pd.getReadMethod();
            Object routeFieldObj = readMethod.invoke(paramObject);
            rebuiltMap.put(suffixName,tableRouter.route(routeFieldObj+""));
            LOGGER.info("paramObject[JavaBean] -> rebuiltMap={}",rebuiltMap);

            invocation.getArgs()[1] = rebuiltMap;
            return invocation.proceed();
        }catch (IntrospectionException e){
            LOGGER.error("check {} {} whether has GET method",paramObject.getClass(),routeField);
            throw e;
        }

    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target,this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    /**
     *
     * @param mapperClass
     * @param methodName
     * @return
     */
    private Method getMethodByName(Class mapperClass,String methodName){
        Method[] declaredMethods = mapperClass.getDeclaredMethods();
        for (Method method : declaredMethods) {
            // mybatis中的mapper是不会有方法重载的
            if(method.getName().equals(methodName)){
                return method;
            }
        }
        return null;
    }
}
