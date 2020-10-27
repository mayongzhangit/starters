package com.myz.starters.aspect.method.advice;

import com.myz.starters.aspect.method.annotation.LoggerName;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author yzMa
 * @desc
 * @date 2020/7/7 19:47
 * @email 2641007740@qq.com
 */
public class ParamRetValPrinterAdvice implements MethodInterceptor {

    private Logger LOGGER = LoggerFactory.getLogger("PARAM-RETVAL-PRINTER");

    @Value("${aspect.printer.param.enabled:true}")
    private boolean printParam;

    @Value("${aspect.printer.retVal.enabled:false}")
    private boolean printRetVal;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (Object.class.equals(invocation.getMethod().getDeclaringClass())){
            return invocation.proceed();
        }
        // @Slfj @Retention(RetentionPolicy.SOURCE) 程序runtime截断是获取不到的
        Class klass = invocation.getMethod().getDeclaringClass();
        String logName = klass.getName();
        LoggerName loggerNameAnno = (LoggerName) klass.getDeclaredAnnotation(LoggerName.class);
        if (loggerNameAnno!=null && loggerNameAnno.topic()!=null && loggerNameAnno.topic().trim().length()>0){
            logName = loggerNameAnno.topic();
        }
        Logger logger = LoggerFactory.getLogger(logName);

        long startTime = System.currentTimeMillis();
        String declaration = buildMethodDeclaration(invocation);
        Object[] arguments = invocation.getArguments(); // not null --> []
        StringBuilder argBuilder = new StringBuilder();
        for (Object argObj : arguments){
            if (needExtraHandle(argObj)){
                doExtraHandle(argObj,argBuilder);
                continue;
            }
            argBuilder.append(argObj).append(",");
        }
        String paramStr = null;
        if (argBuilder.length() >0){
            paramStr = argBuilder.toString().substring(0,argBuilder.length()-1);
        }else {
            paramStr = argBuilder.toString();
        }

        if (printParam){
            if (LOGGER.isDebugEnabled()){
                LOGGER.debug("declaration={},params={},startTime={}",declaration,paramStr,startTime);
            }else {
                logger.info("declaration={},params={},startTime={}",declaration,paramStr,startTime);
            }
        }

        Object result = invocation.proceed();

        long endTime = System.currentTimeMillis();

        if (printRetVal){
            if (LOGGER.isDebugEnabled()){
                LOGGER.debug("declaration={},retVal={},endTime={},cost={}ms",declaration,result,endTime,endTime-startTime);
            }else {
                logger.info("declaration={},retVal={},endTime={},cost={}ms",declaration,result,endTime,endTime-startTime);
            }
        }

        return result;
    }

    /**
     * 是否需要额外处理
     * @param argObj
     * @return
     */
    protected boolean needExtraHandle(Object argObj) {
        return false;
    }

    /**
     * 允许子类进行一些额外的处理
     * @param argObj
     * @param argBuilder
     */
    protected void doExtraHandle(Object argObj, StringBuilder argBuilder) {
    }

    /**
     * 构建声明
     * @param invocation
     * @return
     */
    private String buildMethodDeclaration(MethodInvocation invocation) {
        String className = invocation.getThis().getClass().getSimpleName();
        String methodName = invocation.getMethod().getName();
        StringBuilder sb = new StringBuilder();
        sb.append(className).append("#").append(methodName).append("(");
        Type[] genericParameterTypes = invocation.getMethod().getGenericParameterTypes();
        if (genericParameterTypes == null || genericParameterTypes.length == 0) {
            sb.append(")");
            return sb.toString();
        }
        for (Type genericParameterType : genericParameterTypes) {
            if(genericParameterType instanceof ParameterizedType){
                ParameterizedType parameterizedType = (ParameterizedType)genericParameterType;

                sb.append(((Class)parameterizedType.getRawType()).getSimpleName());
                StringBuilder actualTypeBuilder = new StringBuilder("<");

                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                for (Type actualType : actualTypeArguments){
//                    actualTypeBuilder.append(((Class)actualType).getName()).append(",");
                    actualTypeBuilder.append(actualType.getTypeName()).append(",");
                }
                String actualTypeStr = actualTypeBuilder.substring(0, actualTypeBuilder.length() - 1);
                sb.append(actualTypeStr).append(">").append(",");
            }else {
                sb.append(((Class)genericParameterType).getSimpleName()).append(",");
            }
        }
        String result = sb.substring(0,sb.length()-1);
        result+=")";
        return result;
    }
}
