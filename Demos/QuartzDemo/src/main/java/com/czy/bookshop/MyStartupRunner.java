package com.czy.bookshop;

import com.czy.bookshop.job.MyScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyStartupRunner implements CommandLineRunner {
    //项目一启动，则会加载所有的CommandLineRunner 的 run 方法
    @Autowired
    private MyScheduler myScheduler;
    @Override
    public void run(String... args) throws Exception {
//        myScheduler.start();
    }
}
