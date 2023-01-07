package com.czy.qiantai;

import com.czy.qiantai.entity.User;
import com.czy.qiantai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@SpringBootApplication
@Controller
public class QianTaiMain {
    public static void main(String[] args) {
        SpringApplication.run(QianTaiMain.class);
    }

    @Autowired
    private UserService userService;
    @RequestMapping("/")
    @ResponseBody
    public List<User> testCode(){
        List<User> list = userService.list();
        return list;
    }
}