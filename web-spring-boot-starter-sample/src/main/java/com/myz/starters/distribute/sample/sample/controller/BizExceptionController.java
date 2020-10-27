package com.myz.starters.distribute.sample.sample.controller;

import com.myz.common.util.ApiResult;
import com.myz.starters.distribute.sample.web.controller.annotation.MyzControllerAdviceAnno;
import com.myz.starters.distribute.sample.web.controller.exception.MyzBizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yzMa
 * @desc
 * @date 2020/10/13 16:10
 * @email 2641007740@qq.com
 */
@Slf4j
@RestController
@RequestMapping("biz-ex")
@MyzControllerAdviceAnno
public class BizExceptionController {

    @GetMapping("/test")
    public ApiResult test(boolean exFlag){
        if (exFlag){
            throw new MyzBizException("manual ex","人为抛出异常");
        }
        return ApiResult.OK("test ok");
    }
}
