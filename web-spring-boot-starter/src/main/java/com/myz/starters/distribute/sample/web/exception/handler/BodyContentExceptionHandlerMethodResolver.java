package com.myz.starters.distribute.sample.web.exception.handler;

import com.myz.common.util.ApiResult;
import com.myz.starters.distribute.sample.web.annotation.BodyContentMethodArgumentResolver;
import com.myz.starters.distribute.sample.web.exception.BodyContentResolverException;
import com.myz.starters.distribute.sample.web.exception.annotation.BodyContentExceptionAdvice;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author yzMa
 * @desc {@link org.springframework.boot.autoconfigure.web.servlet.error.DefaultErrorViewResolver} 这个只能解决http code为4xx,5xx的，这里http code是正常的但
 * @date 2020/6/28 11:55
 * @email 2641007740@qq.com
 */
@ControllerAdvice(annotations = {BodyContentExceptionAdvice.class})
public class BodyContentExceptionHandlerMethodResolver {

    /**
     * {@link BodyContentMethodArgumentResolver}的异常处理类
     * @param ex
     */
    @ResponseBody
    @ExceptionHandler(BodyContentResolverException.class)
    public ApiResult handlerBodyContentResolverException(BodyContentResolverException ex){
        return ApiResult.build("1010", "参数错误");
    }
}
