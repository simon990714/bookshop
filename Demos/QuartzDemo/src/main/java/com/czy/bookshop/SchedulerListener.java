package com.czy.bookshop;

import com.czy.bookshop.job.MyScheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.Format;
import java.util.Date;

@Component
@EnableScheduling
@Configuration
public class SchedulerListener {

    @Autowired
    public MyScheduler myScheduler;

    @Scheduled(cron = "0 20 16 * * ?")
    public void schedule() throws SchedulerException {
        myScheduler.start();
        System.out.println("定时任务开始执行" + new Date());
    }
}
