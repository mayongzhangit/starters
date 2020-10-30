package com.myz.starters.distribute.sample.web.controller.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myz.common.util.ApiResult;
import com.myz.starters.distribute.sample.web.controller.annotation.MyzControllerAdviceAnno;
import com.myz.starters.distribute.sample.web.controller.exception.MyzBizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author yzMa
 * @desc
 * @date 2020/10/13 15:43
 * @email 2641007740@qq.com
 */
@RestControllerAdvice(annotations = MyzControllerAdviceAnno.class)
public class MyzControllerAdvice {

    private Logger LOGGER = LoggerFactory.getLogger(MyzControllerAdvice.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    @ExceptionHandler(Throwable.class)
    public ApiResult handle(Throwable throwable) throws JsonProcessingException {
        if (throwable instanceof MyzBizException){
            MyzBizException wzException = (MyzBizException) throwable;
            LOGGER.info("myzEx code={} ,msg={},params={}",wzException.getCode(),wzException.getMsg(), objectMapper.writeValueAsString(wzException.getParams()));
            return ApiResult.build(wzException.getCode(),wzException.getMsg());
        }
        LOGGER.error("myzEx error e",throwable);
        return ApiResult.error(throwable.getMessage());
    }
}
