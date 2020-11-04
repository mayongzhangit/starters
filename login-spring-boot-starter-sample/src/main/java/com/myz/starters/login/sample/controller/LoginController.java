package com.myz.starters.login.sample.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myz.common.util.ApiResult;
import com.myz.starters.login.annotation.LoginRequired;
import com.myz.starters.login.util.CookieUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yzMa
 * @desc
 * @date 2020/11/4 11:50
 * @email 2641007740@qq.com
 */
@Slf4j
@RestController
@RequestMapping("/")
public class LoginController {


    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 通常是一个表单提交过来
     *
     * @param user
     * @return
     */
    @LoginRequired(required = false)
    @PostMapping("/login")
    public ApiResult login(User user, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        CookieUtil.set(objectMapper.writeValueAsString(user),request,response);
        return ApiResult.OK(user);
    }

    @Data
    class User{
        private String name;
        private String passwd;
    }
}
