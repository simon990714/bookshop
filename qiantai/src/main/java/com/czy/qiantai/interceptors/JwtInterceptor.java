package com.czy.qiantai.interceptors;

import com.czy.qiantai.utils.CookieUtils;
import com.czy.qiantai.utils.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.获取cookie中的token值
        String userTokenFromCookie = CookieUtils.getUserTokenFromCookie(request);
        String account = "";
        //2.获取token中的account值
        try{
            if (!StringUtils.isEmpty(userTokenFromCookie)){
                account = JwtUtils.getAccount(userTokenFromCookie);
            }
        }
        //3.有过期异常则去redis里寻找
        catch (ExpiredJwtException expiredJwtException){
            String redisAccount = stringRedisTemplate.opsForValue().get(userTokenFromCookie);
            if (!StringUtils.isEmpty(redisAccount)){
                //删除旧数据
                stringRedisTemplate.delete(userTokenFromCookie);
                //更新cookie和redis中的token
                String newToken = JwtUtils.createToken(redisAccount, 15);
                CookieUtils.setUserToken2Cookie(response,newToken);
                stringRedisTemplate.opsForValue().set(newToken,redisAccount,60, TimeUnit.MINUTES);
            }
        }
        return true;
    }
}
