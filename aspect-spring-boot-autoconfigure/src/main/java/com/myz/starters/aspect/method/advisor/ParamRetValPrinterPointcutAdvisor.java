package com.myz.starters.aspect.method.advisor;

import com.myz.starters.aspect.method.advice.ParamRetValPrinterAdvice;
import com.myz.starters.aspect.method.annotation.ParamRetValPrinter;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;

/**
 * @author yzMa
 * @desc
 * @date 2020/7/7 19:17
 * @email 2641007740@qq.com
 */
public class ParamRetValPrinterPointcutAdvisor extends AbstractPointcutAdvisor {

    private ParamRetValPrinterAdvice advice;

    public ParamRetValPrinterPointcutAdvisor(ParamRetValPrinterAdvice advice){
        this.advice = advice;
    }

    @Override
    public Pointcut getPointcut() {
        return new AnnotationMatchingPointcut(ParamRetValPrinter.class,false);
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }
}
