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
 * 参考：
 *       https://developer.51cto.com/art/202002/610076.htm 讲的最深入
 *
 *       https://www.iteye.com/blog/elim-2426566
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
     *
     * 跟Callable不同点：
     *  1）不需要独立的线程池
     *  2）不会自动提交任务，需要通过设置Deferred#setResult()
     *
     * 实质是利用Servlet3.0的AsyncContext demo可以参考 {@link MyAsyncServlet}  目前是有瑕疵的
     *
     *
     * Java Web异步处理的整体逻辑思想 异步处理逻辑只是一个规范(接口)
     * 1、AsyncContext startAsync() 自己把执行流程交出去
     * 2、void dispatch() 别人把执行流程还回来
     * 传说中“牛X和高大上”的异步处理，到最后也不过区区十四个字，“执行流程交出去，执行流程还回来”，可能还会有人觉得，异步处理不应该和客户端也有关系吗?其实并没有，纯粹是服务器端的把戏。而且对于单个请求的处理时间也不会减少。
     * 对于第一类，我们返回Callable类型就可以了，这样SpringMvc会把它提交到线程池里去执行。SpringMvc有自己的线程
     * 对于第二类，我们返回DeferredResult类型即可，它的意思是延迟结果，就是需要延迟一会才会有结果，但是如何延迟呢，SpringMvc并不会去管。
     * 因此是由开发人员来决定的，开发人员需要自己弄个线程去执行（请求另一个controller也算是用了一个线程的），并且一定要记住最后要设置一下结果才行
     *
     * 耗时的代码都跑到别的线程里去执行了，那么SpringMvc的处理主流程自然就结束了，这就是执行流程交出去的过程。
     * 那么一段时间后，别的线程池运行结束，已经取得了结果，那这个结果和执行流程又该如何还回来呢?
     * 其实是别的线程池在处理结束并得到结果后，处理流程连同结果会返回到Web容器中，由Web容器再次分派这个请求到SpringMvc中来。
     * 这就是为什么上面第二个方法的名字叫做dispatch的原因，就是再次分派这个请求到Web应用中来嘛，因此会再次进入到SpringMvc中，这不就把执行流程还回来了嘛。
     * 可能会有人产生疑惑，如果SpringMvc再次处理这个请求那不就乱套了吗?答案是显然不会的，因为这次异步的结果已经存在了，自然不会再异步处理了，而是把它跳过去直接进入后续处理。
     * 也就是对方法返回值(ReturnValue)的处理，比如序列化成JSON写入响应，或进行视图渲染，把渲染后的视图写入响应，这样这个请求就算处理完毕了。
     *
     *
     * 规范是SpringMvc借助于Servlet3实现了异步规范：
     * DeferredResult springmvc 不会帮你处理任务，需要你自己设置任务 DeferredController#handleConfigChanged(com.myz.starters.dynamic.refresh.sample.event.MyConfigChangeEvent)
     * 流程：
     * 步骤一：DispatcherServlet --> RequestMappingHandlerAdapter#invokeHandlerMethod
     *                           --> 创建 WebAsyncManager 并request#setAttribute(key,WebAsyncManager)
     *                           --> asyncManager.hasConcurrentResult()
     *                                  是，不在调用Controller                   <------------------------------------------------------------------------------
     *                                  否，调用controller --> 返回DeferredResult<String>                                                                      |
     *                           --> DeferredResultMethodReturnValueHandler    这是我们Controller返回的，并且存储到成员变量的DeferredResult                                                                              |
     *                                              DeferredResult#setResultHandler(DeferredResultHandler) // A 重要！！！ 是一个lambada表达式                                     |
     *                                              从req#getAttribute(key)得到WebAsyncManager#startAsyncProcess                                               |
     *                                            --> req.startAsync: AsyncContext 交出去过程                                                                           |
     *                                                                                                                                                        |
     * 步骤二：DispatcherServlet --> RequestMappingHandlerAdapter#invokeHandlerMethod     这是我们调用另一个Controller之后并执行了DeferredResult#setResult  跟A处是同一个                                                                        |
     *                           --> 得到步骤一相同的DeferredResult#setResult(result)                                                                            |
     *                           --> 调用步骤一中A处设置的lambada设置的Handler 然后执行DeferredResultHandler#handleResult A处实际为一个lambada表达，记录着request发起                                    |
     *                                                              ----> request.dispatch（同步骤一是同一个request） 还回来过程                                            |
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
