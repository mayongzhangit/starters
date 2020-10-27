package com.myz.starters.dynamic.refresh.sample.controller;

import com.myz.starters.dynamic.refresh.sample.event.MyConfigChangeEvent;
import com.myz.starters.dynamic.refresh.sample.servlet.MyAsyncServlet;
import com.myz.starters.dynamic.refresh.sample.util.PrintUtil;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yzMa
 * @desc
 * 参考： https://www.iteye.com/blog/elim-2426566
 *       https://developer.51cto.com/art/202002/610076.htm
 * @date 2020/7/17 14:27
 * @email 2641007740@qq.com
 */
@RestController
@RequestMapping("/deferred")
public class DeferredController {

    private Map<String,DeferredResult<String>> deferredResultMap = new ConcurrentHashMap<>();

    /***
     *
     *
     * 跟Callable不同点：
     *  1）不需要独立的线程池
     *  2）不会自动提交任务，需要通过设置Deferred#setResult()
     *
     * 实质是利用Servlet3.0的AsyncContext demo可以参考 {@link MyAsyncServlet}
     *
     * DeferredResult springmvc 不会帮你处理任务，需要你自己设置任务 DeferredController#handleConfigChanged(com.myz.starters.dynamic.refresh.sample.event.MyConfigChangeEvent)
     * 流程：
     * 步骤一：DispatcherServlet --> RequestMappingHandlerAdapter#invokeHandlerMethod
     *                           --> 创建 WebAsyncManager 并request#setAttribute(key,WebAsyncManager)
     *                           --> asyncManager.hasConcurrentResult()
     *                                  是，不在调用Controller                   <------------------------------------------------------------------------------
     *                                  否，调用controller --> 返回DeferredResult<String>                                                                      |
     *                           --> DeferredResultMethodReturnValueHandler                                                                                  |
     *                                              DeferredResult#setResultHandler(DeferredResultHandler) // A 重要！！！                                     |
     *                                              从req#getAttribute(key)得到WebAsyncManager#startAsyncProcess                                               |
     *                                            --> req.startAsync: AsyncContext                                                                            |
     *                                                                                                                                                        |
     * 步骤二：DispatcherServlet --> RequestMappingHandlerAdapter#invokeHandlerMethod                                                                           |
     *                           --> 得到步骤一相同的DeferredResult#setResult(result)                                                                            |
     *                           --> 调用步骤一中A处DeferredResultHandler#handleResult A处实际为一个lambada表达，记录着request发起                                    |
     *                                                              ----> request.dispatch（同步骤一是同一个request）                                             |
     * （dispatcher步骤一的请求）DispatcherServlet ---------------------------------------------------------------------------------------------------------------
     * @param configKey
     * @return
     */
    @GetMapping(value = "/long-polling-get/{configKey}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public DeferredResult<String> longPollingGetConfigKey(@PathVariable String configKey){

        DeferredResult<String> configValResult = new DeferredResult<>(60*1000L,"val not change");
        PrintUtil.print("entry long polling");
        configValResult.onTimeout(new Runnable() {
            @Override
            public void run() {
                PrintUtil.print("超时了，返回超时配置信息");
                deferredResultMap.remove(configKey);
            }
        });
        configValResult.onCompletion(new Runnable() {
            @Override
            public void run() {
                PrintUtil.print("返回结果了");
                deferredResultMap.remove(configKey);
            }
        });
        deferredResultMap.put(configKey,configValResult);
        PrintUtil.print("return DeferredResult<String>");
        return configValResult;
    }

    /**
     * 设置完结果之后
     * @param myConfigChangeEvent
     */
    @EventListener(MyConfigChangeEvent.class)
    public void handleConfigChanged(MyConfigChangeEvent myConfigChangeEvent){
        String configKey = myConfigChangeEvent.getConfigKey();
        DeferredResult<String> stringDeferredResult = deferredResultMap.get(configKey);
        stringDeferredResult.setResult(myConfigChangeEvent.getChangedVal());
    }
}
