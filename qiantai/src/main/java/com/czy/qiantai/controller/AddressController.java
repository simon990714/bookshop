package com.czy.qiantai.controller;


import com.czy.qiantai.entity.Address;
import com.czy.qiantai.entity.User;
import com.czy.qiantai.service.AddressService;
import com.czy.qiantai.service.UserService;
import com.czy.qiantai.utils.CookieUtils;
import com.czy.qiantai.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author czy
 * @since 2023-01-07 12:01:18
 */
@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private UserService userService;
    @Autowired
    private AddressService addressService;

    @RequestMapping("allAddress")
    public List<Address> allAddress(HttpServletRequest request) {
        User currentUser = getCurrentUser(request);
        return addressService.getAddressByAccount(currentUser.getId());
    }


    private User getCurrentUser(HttpServletRequest request) {
        String userTokenFromCookie = CookieUtils.getUserTokenFromCookie(request);
        String currentUserAccount = JwtUtils.getAccountWithoutException(userTokenFromCookie);
        User currentUser = userService.getUserByAccount(currentUserAccount);

        return currentUser;
    }

    @PostMapping("saveAddress")
    public String saveAddress(Address address, HttpServletRequest request) {
        if (StringUtils.isEmpty(address.getProvince()) || StringUtils.isEmpty(address.getCity()) ) {
            return "有必选项未填！";
        }
        User currentUser = getCurrentUser(request);
        address.setUserId(currentUser.getId());
        address.setStatus("1");
        addressService.saveAddress(address);
        return "ok";
    }
}

