package com.myz.starters.aspect.method.advice.web;

import com.myz.starters.aspect.method.advice.ParamRetValPrinterAdvice;
import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author yzMa
 * @desc
 * @date 2020/7/10 14:51
 * @email 2641007740@qq.com
 */
public class WebParamRetValPrinterAdvice extends ParamRetValPrinterAdvice {

    @Override
    protected boolean needExtraHandle(Object argObj) {
        return argObj instanceof HttpServletRequest;
    }

    @Override
    protected void doExtraHandle(Object argObj, StringBuilder argBuilder) {
        HttpServletRequest request = (HttpServletRequest)argObj;
        String method = request.getMethod();
        argBuilder.append("HttpServletRequest=");
        if(HttpMethod.GET.name().equals(method)) {
            argBuilder.append(request.getQueryString()).append(",");
        } else if(HttpMethod.POST.name().equals(method)) {
            argBuilder.append(getRequestBody(request)).append(",");
        } else {
            argBuilder.append(request.getParameterMap()).append(",");
        }
    }

    /**
     *
     * @param httpServletRequest
     * @return
     */
    private String getRequestBody(HttpServletRequest httpServletRequest){
        BufferedReader br;
        try {
            String str, wholeStr = "";
            br = httpServletRequest.getReader();
            while((str = br.readLine()) != null){
                wholeStr += str;
            }
            return wholeStr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
