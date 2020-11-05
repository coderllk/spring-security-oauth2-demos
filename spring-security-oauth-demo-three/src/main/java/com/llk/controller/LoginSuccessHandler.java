package com.llk.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    /**
     当用户登录成功后，就执行该方法
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        System.out.println("登录成功");
        System.out.println(authentication);
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write("LoginSuccessHandler 登录成功");

    }
}
