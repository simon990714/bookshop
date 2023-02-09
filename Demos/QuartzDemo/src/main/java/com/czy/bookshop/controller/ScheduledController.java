package com.czy.bookshop.controller;

import com.czy.bookshop.job.MyScheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScheduledController {
    @Autowired
    private MyScheduler myScheduler;
    @RequestMapping("/")
    public String testScheduler(){
        try {
            myScheduler.start();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
        return myScheduler.toString();
    }

}
