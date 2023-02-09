package com.czy.bookshop.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledJob1 implements Job {
    private String name ;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        System.out.println("ScheduledJob1 111111111111111 is running ... + "
                + name + "  ---->  " + dateFormat.format(new Date()));
    }

}