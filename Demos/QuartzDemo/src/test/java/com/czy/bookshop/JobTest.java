package com.czy.bookshop;

import com.czy.bookshop.job.MyScheduler;
import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JobTest {

    @Autowired
    MyScheduler myScheduler;

    @Test
    void testJob() throws SchedulerException {

        myScheduler.start();

        //保持运行
        while (0<1){}
    }
}
