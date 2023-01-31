package com.czy.qiantai.controller;


import com.czy.qiantai.entity.User;
import com.czy.qiantai.service.UserService;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author czy
 * @since 2023-01-07 12:01:19
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private Producer producer;

    @Autowired
    private UserService userService;

    @RequestMapping("getKaptchaImage")
    public void getKaptchaImage(HttpServletResponse response,HttpSession session) {

        //生成验证码
        String codeText = producer.createText();
        BufferedImage codeImage = producer.createImage(codeText);
        session.setAttribute("codeText",codeText);

        //写出
        response.setContentType("image/png");

        try {
            ImageIO.write(codeImage, "png", response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("login")
    public String login(String username, String password, String code, HttpSession session, Model model){
        Object trueCode = session.getAttribute("codeText");

        //检验验证码
        if (StringUtils.isEmpty(code) || !trueCode.toString().equalsIgnoreCase(code)){
            model.addAttribute("errorInfo","验证码错误");
            return "login";
        }

        //检验用户名密码
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)||username.equals("")||password.equals("")){
            model.addAttribute("errorInfo","账号密码不能为空");
            return "login";
        }

        User user = userService.getUserByAccount(username);
        if (null == user || !password.equals(user.getPassword())){
            model.addAttribute("errorInfo","账号或密码错误");
            return "login";
        }

        //登录成功
        session.setAttribute("currentAccount",user);
        return "redirect:/";
    }

    @RequestMapping("getCurrentAccount")
    @ResponseBody
    public String getCurrentAccount(HttpSession session){
        String currentAccount = session.getAttribute("currentAccount").toString();
        return currentAccount;
    }

    @RequestMapping("logout")
    public String logout(HttpSession session){
        session.removeAttribute("currentAccount");
        return "redirect:/";
    }

}

