package com.czy.qiantai;

import com.czy.qiantai.entity.User;
import com.czy.qiantai.service.UserService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@SpringBootApplication
@EnableCaching
@EnableRabbit
public class QianTaiMain {
    public static void main(String[] args) {
        SpringApplication.run(QianTaiMain.class);
    }

}