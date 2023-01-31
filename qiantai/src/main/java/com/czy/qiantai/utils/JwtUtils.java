package com.czy.qiantai.utils;

import io.jsonwebtoken.*;

import java.util.Date;

public class JwtUtils {

    private static String secret = "qwerasdfzxc123abcdefg`!@#$%^&*()_+";

    //生成token
    public static String createToken(String account, long minutes) {
        JwtBuilder builder = Jwts.builder();

        //header
        builder.setHeaderParam("alg", "HS256"); //加密算法
        builder.setHeaderParam("typ", "JWT");

        //payload载荷
        builder.setIssuer("CZY");//签发人
        builder.setSubject("蜗牛学员");//面向对象
        builder.setExpiration(new Date(new Date().getTime() + minutes * 60 * 1000));//到期时间

        //自定义信息
        builder.claim("account", account);

        //设置签名
        builder.signWith(SignatureAlgorithm.HS256, secret);

        //输出token
        String token = builder.compact();
        return token;
    }
//解析token为claims
    public static Claims parseClaims(String token) {
        JwtParser parser = Jwts.parser();
        Jws<Claims> claimsJws = parser.setSigningKey(secret).parseClaimsJws(token);
        Claims body = claimsJws.getBody();
        return body;
    }

    public static String getAccount(String token) {
        Claims claims = parseClaims(token);
        return (String) claims.get("account");
    }

//一定会返回一个account字符串
    public static String getAccountWithoutException(String token) {

        String account = "";
        try{
            account = getAccount(token);
        }catch (Exception e){
            e.printStackTrace();
        }
        return account;
    }



}
