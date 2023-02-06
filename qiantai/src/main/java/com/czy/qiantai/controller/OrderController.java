package com.czy.qiantai.controller;


import com.czy.qiantai.entity.User;
import com.czy.qiantai.service.OrderService;
import com.czy.qiantai.service.UserService;
import com.czy.qiantai.utils.CookieUtils;
import com.czy.qiantai.utils.JwtUtils;
import com.czy.qiantai.vo.CartOrder;
import com.czy.qiantai.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author czy
 * @since 2023-01-07 12:01:19
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;




    @RequestMapping("getAllItems")
    @ResponseBody
    public CartOrder getAllItems(HttpServletRequest request, @RequestParam("bookIds[]") Long[] bookIds) {
        //获取当前用户id
        User userByAccount = getCurrentUser(request);
        return orderService.getCartOrder(userByAccount.getId(),bookIds);
    }


    private User getCurrentUser(HttpServletRequest request){
        String userTokenFromCookie = CookieUtils.getUserTokenFromCookie(request);
        String currentUserAccount = JwtUtils.getAccountWithoutException(userTokenFromCookie);
        User currentUser = userService.getUserByAccount(currentUserAccount);

        return currentUser;
    }

    @RequestMapping("createOrder")
    @ResponseBody
    public String createOrder(HttpServletRequest request, @RequestParam("bookIds[]") Long[] bookIds ,@RequestParam("defaultAddressId") Long addressId ) {
        //获取当前用户id
        User userByAccount = getCurrentUser(request);
        if (orderService.createOrder(userByAccount.getId(),bookIds,addressId) == 1){
            return "ok";
        }
        return "提交订单失败";
    }


    @RequestMapping("getOrderVo")
    @ResponseBody
    public List<OrderVo> getOrderVo(HttpServletRequest request ) {
        //获取当前用户id
        User userByAccount = getCurrentUser(request);
        return orderService.getOrderVo(userByAccount.getId());
    }







}

