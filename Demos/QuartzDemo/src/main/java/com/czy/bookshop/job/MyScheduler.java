package com.czy.bookshop.job;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class MyScheduler {
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    //包含所有  jobDetail  和  trigger
    Scheduler scheduler;

    public void start() throws SchedulerException {
        //先用工厂创建一个空白的
        scheduler = schedulerFactoryBean.getScheduler();

        scheduleJob1(scheduler);
        scheduleJob2(scheduler);
    }

    public void shutdown() throws SchedulerException {
        scheduler.shutdown();
    }

    public void scheduleJob1(Scheduler scheduler) throws SchedulerException {
        //组及名称是 Scheduler 查找定位容器中某一对象的依据，Trigger 的组及名称必须唯一，JobDetail 的组和名称也必须唯一
        //但可以和 Trigger 的组和名称相同，因为它们是不同类型的

        //1.job
        JobDetail jobDetail = JobBuilder.newJob(ScheduledJob1.class).withIdentity("job1", "group1").build();


        //2.触发器
        //withSchedule需要一个ScheduleBuilder，这里可以选择simple和cron，在这里规定时间间隔
        /**
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/10 * * * * ? ");

        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger1","group1")
                .usingJobData("name","蜗牛1")//在这里定义要使用到的job数据
                .withSchedule(cronScheduleBuilder).build();
         */
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(3).repeatForever();
        SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger().withIdentity("trigger1","group1")
                .usingJobData("name","蜗牛simple")//在这里定义要使用到的job数据
                .withSchedule(simpleScheduleBuilder).build();

        //3.调度指定的 触发器和任务
        scheduler.scheduleJob(jobDetail,simpleTrigger);

    }


    public void scheduleJob2(Scheduler scheduler) throws SchedulerException {
        //组及名称是 Scheduler 查找定位容器中某一对象的依据，Trigger 的组及名称必须唯一，JobDetail 的组和名称也必须唯一
        //但可以和 Trigger 的组和名称相同，因为它们是不同类型的

        //1.job
        JobDetail jobDetail = JobBuilder.newJob(ScheduledJob1.class).withIdentity("job2", "group2").build();


        //2.触发器
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/10 * * * * ? ");

        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger2","group2")
                .usingJobData("name","蜗牛cron")//在这里定义要使用到的job数据
                .withSchedule(cronScheduleBuilder).build();

        //3.调度指定的 触发器和任务
        scheduler.scheduleJob(jobDetail,cronTrigger);

    }

}
