package com.czy.qiantai.controller;


import com.czy.qiantai.entity.User;
import com.czy.qiantai.service.MyCartService;
import com.czy.qiantai.service.UserService;
import com.czy.qiantai.utils.CookieUtils;
import com.czy.qiantai.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Collection;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author czy
 * @since 2023-01-07 12:01:19
 */
@Controller
@RequestMapping("mycart")
public class MycartController {

    @Autowired
    private UserService userService;
    @Autowired
    private MyCartService myCartService;



    @RequestMapping("add")
    @ResponseBody
    public String add(Long bookId,HttpServletRequest request) {
        //获取当前用户id
        String userTokenFromCookie = CookieUtils.getUserTokenFromCookie(request);
        String account = JwtUtils.getAccount(userTokenFromCookie);
        User userByAccount = userService.getUserByAccount(account);
        myCartService.addCart(userByAccount.getId(), bookId);
        return "ok";
    }

    @RequestMapping("getAllItems")
    @ResponseBody
    public Collection<Object> getAllItems(HttpServletRequest request) {
        //获取当前用户id
        String userTokenFromCookie = CookieUtils.getUserTokenFromCookie(request);
        String account = JwtUtils.getAccount(userTokenFromCookie);
        User userByAccount = userService.getUserByAccount(account);
        Collection<Object> allItems = myCartService.getAllItems(userByAccount.getId());
        return allItems;
    }


    @RequestMapping("updateItemNum")
    @ResponseBody
    public void updateItemNum(HttpServletRequest request,Long bookId,Integer itemNum) {
        //获取当前用户id
        String userTokenFromCookie = CookieUtils.getUserTokenFromCookie(request);
        String account = JwtUtils.getAccount(userTokenFromCookie);
        User userByAccount = userService.getUserByAccount(account);
        myCartService.updateItemNum(userByAccount.getId(),bookId,itemNum);
    }



    @RequestMapping("calTotalPrice")
    @ResponseBody
    public BigDecimal calTotalPrice(HttpServletRequest request,@RequestParam("bookIds[]") Long[] bookIds) {
        //获取当前用户id
        String userTokenFromCookie = CookieUtils.getUserTokenFromCookie(request);
        String account = JwtUtils.getAccount(userTokenFromCookie);
        User userByAccount = userService.getUserByAccount(account);

        return myCartService.calTotalPrice(userByAccount.getId(),bookIds);

    }







}

