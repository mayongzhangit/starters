package com.myz.starters.login.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yzMa
 * @desc
 * @date 2020/6/18 9:59 PM
 * @email 2641007740@qq.com
 */
public class CookieUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(CookieUtil.class);

    private final static String JWT_TOKEN = "jwt-token"; // TODO 加上应用名web

    /**
     * @param request
     * @return
     * @desc 按顺序分别从 header/parameter/cookie中抽取token
     */
    public static String extractToken(HttpServletRequest request) {
        String headerToken = request.getHeader(JWT_TOKEN);
        if (headerToken != null && headerToken.trim().length() > 0) {
            LOGGER.debug("headerToken={}", headerToken);
            return headerToken;
        }

        String paramToken = request.getParameter(JWT_TOKEN);
        if (paramToken != null && paramToken.trim().length() > 0) {
            LOGGER.debug("paramToken={}", paramToken);
            return paramToken;
        }

        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            LOGGER.debug("no cookie found ");
            return null;
        }

        for (Cookie cookie : cookies) {
            if (JWT_TOKEN.equals(cookie.getName())) {
                String cookieToken = cookie.getValue();
                if (cookieToken != null && cookieToken.trim().length() > 0) {
                    LOGGER.debug("cookieToken={}", cookieToken);
                    return cookieToken;
                }
            }
        }
        LOGGER.info("no token found !");
        return null;
    }

    /**
     * 获取token对应点userJson
     *
     * @param token
     * @return userJson
     */
    public static String get(String token) {
        return JWTUtil.verifyToken(token);
    }

    /**
     * @param userJson
     * @param domain
     * @param httpOnly
     * @param response
     */
    public static void set(String userJson, String domain, boolean httpOnly,
                           HttpServletRequest request,HttpServletResponse response) {
        int maxInactiveInterval = request.getSession().getMaxInactiveInterval();
        String token = JWTUtil.createToken(userJson, maxInactiveInterval);
        LOGGER.info("setCookie session maxInactiveInterval={} userJson={},token={},",maxInactiveInterval, userJson,token);

        response.addHeader(JWT_TOKEN, token);

        Cookie cookie = new Cookie(JWT_TOKEN, token);
        if (!StringUtils.isBlank(domain)) {
            cookie.setDomain(domain);
        }
        cookie.setPath("/");
        cookie.setMaxAge(-1);
        cookie.setHttpOnly(httpOnly);

        response.addCookie(cookie);
    }

    /**
     * @param userJson
     * @param response
     */
    public static void set(String userJson,HttpServletRequest request, HttpServletResponse response) {
        set(userJson, null, true, request,response);
    }
}
