package com.myz.starters.login.interceptor;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myz.common.util.ApiResult;
import com.myz.starters.login.annotation.LoginRequired;
import com.myz.starters.login.context.LoginContext;
import com.myz.starters.login.util.CookieUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * @author yzMa
 * @desc
 * @date 2020/6/19 12:18 AM
 * @email 2641007740@qq.com
 */
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter {

    private Logger LOGGER = LoggerFactory.getLogger(LoginHandlerInterceptor.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return super.preHandle(request, response, handler);
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        if (request.getRequestURI().contains("/error")) {
            return super.preHandle(request, response, handler);
        }
        LoginRequired methodLoginRequired = method.getDeclaredAnnotation(LoginRequired.class);
        if (methodLoginRequired != null && methodLoginRequired.required() == false) {
            return super.preHandle(request, response, handler);
        }
        LoginRequired classLoginRequired = AnnotationUtils.findAnnotation(method.getDeclaringClass(), LoginRequired.class);
        if ((classLoginRequired != null && classLoginRequired.required() == false)) {
            return super.preHandle(request, response, handler);
        }
        /**
         * 判断是否需返回json
         * @see {@link org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor}
         */
        boolean jsonFlag = false;
        MethodParameter returnType = handlerMethod.getReturnType();
        if (AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), ResponseBody.class) ||
                returnType.hasMethodAnnotation(ResponseBody.class)) {
            jsonFlag = true;
        }
        LOGGER.debug(" method:{} need authorization jsonFlag:{}", method.getName(), jsonFlag);

        String token = CookieUtil.extractToken(request);
        if (token == null || token.trim().length() == 0) {
            flushResult(response, "no-token", "token未找到，请检查【header/parameter/cookie】中是否存在token，不存在则前端发起静默授权，存在请传递");
            return false;
        }
        try {
            String userJson = CookieUtil.get(token);
            LoginContext.set(userJson);
            LOGGER.debug("token:{},userJson={}",token,userJson);
        } catch (TokenExpiredException teex) {
            flushResult(response, "expired-token", "token过期，需前端发起静默授权，后端重新生成一个新Token，前端传递新Token");
            return false;
        } catch (Exception e) {
            LOGGER.error("illegal token={},e", token, e);
            flushResult(response, "illegal-token", "非法token");
            return false;
        }
        return super.preHandle(request, response, handler);
    }

    /**
     * @param response
     * @param code
     * @param msg
     * @throws IOException
     */
    private void flushResult(HttpServletResponse response, String code, String msg) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter writer = response.getWriter();
        writer.write(objectMapper.writeValueAsString(ApiResult.build(code, msg)));
        writer.flush();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
