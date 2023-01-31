package com.czy.qiantai.controller;


import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author czy
 * @since 2023-01-07 12:01:19
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    Producer producer;

    @RequestMapping("/getKaptchaImage")
    public void getKaptchaImage(HttpServletResponse response){

        //生成验证码
        String codeText = producer.createText();
        BufferedImage codeImage = producer.createImage(codeText);

        //写出
        response.setContentType("image/png");

        try {
            ImageIO.write(codeImage,"png",response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}

