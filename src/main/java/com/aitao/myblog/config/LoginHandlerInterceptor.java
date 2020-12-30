package com.aitao.myblog.config;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author : AiTao
 * Date : 2020/10/26
 * Time : 11:13
 * Information : 登录拦截器
 */
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        if(request.getSession().getAttribute("USER") == null){
            response.sendRedirect("/admin/login");
            return false;
        }
        return true;
    }
}
