package com.czy.qiantai.interceptors;

import com.czy.qiantai.utils.CookieUtils;
import com.czy.qiantai.utils.JwtUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class PermissionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userTokenFromCookie = CookieUtils.getUserTokenFromCookie(request);
        try {
            String account = JwtUtils.getAccount(userTokenFromCookie);
        }catch (Exception e){
            response.sendRedirect("/login.html");
//            request.getRequestDispatcher("/user/redirectLoginHtml").forward(request,response);
            return false;
        }
        return true;
    }
}
