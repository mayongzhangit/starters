package com.myz.starters.aspect.method.advice.web;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: wanglei1
 * @Date: 2019/09/23 20:55
 * @Description:
 */
public class ReadBodyHttpServletFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        ReadBodyHttpServletRequest requestWrapper = new ReadBodyHttpServletRequest(httpServletRequest);
        filterChain.doFilter(requestWrapper, httpServletResponse);
    }

    @Override
    public void destroy() {
    }
}
