package com.czy.qiantai;

import io.jsonwebtoken.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class QianTaiMainTest {

    @Test
    void test(){

    }

    String secret = "321sdadsad**)9jh66YHkO)(09JY6%Thgh" ;
    @Test
    void testJWTEncode(){

        JwtBuilder builder = Jwts.builder();

        //header
        builder.setHeaderParam("alg","HS256"); //加密算法
        builder.setHeaderParam("typ","JWT");

        //payload载荷
        builder.setIssuer("CZY");//签发人
        builder.setSubject("蜗牛学员");//面向对象
        long expire = 1*60*1000;
        builder.setExpiration(new Date(new Date().getTime() + expire));//到期时间

        //自定义信息
        builder.claim("currentAccount","czy svp");

        //设置签名
        builder.signWith(SignatureAlgorithm.HS256,secret);

        //输出token
        String token = builder.compact();
        System.out.println(token);
    }

    @Test

    void testParseToken(){
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJDWlkiLCJzdWIiOiLonJfniZvlrablkZgiLCJleHAiOjE2NzUxNjM5MjAsImN1cnJlbnRBY2NvdW50IjoiY3p5IHN2cCJ9.k4N3QUQBVxGFUwUTnHosGLTABOmhe1LO1aZE_qG2kc0";
        JwtParser parser = Jwts.parser();
        Jws<Claims> claimsJws = parser.setSigningKey(secret).parseClaimsJws(token);
        Claims body = claimsJws.getBody();
        Object account = body.get("currentAccount");
        System.out.println(account);
    }

    @Test
    void testJwtUtils(){
        String token = JwtUtils.createToken("123", 1);
        System.out.println(token);
        System.out.println(JwtUtils.parseClaims(token));
        System.out.println(JwtUtils.getAccountWithoutException(token));

    }



}
