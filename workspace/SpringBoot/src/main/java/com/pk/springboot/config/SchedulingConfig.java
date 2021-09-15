package com.pk.springboot.config;

import com.pk.springboot.service.CronService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Stream;

@Configuration
@EnableScheduling
public class SchedulingConfig implements SchedulingConfigurer {

    private String cronExpressions;
    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    @Autowired
    private CronService cronService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        cronExpressions = cronService.getCronById(1);
        Runnable runnableTask = () -> System.out.println("Task executed at ->" + LocalDateTime.now());
        Trigger trigger = new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                String newCronExpression = cronService.getCronById(1);
                if (!StringUtils.equalsAnyIgnoreCase(newCronExpression, cronExpressions)) {
                    taskRegistrar.setTriggerTasksList(new ArrayList<>());
                    configureTasks(taskRegistrar);
                    taskRegistrar.destroy();
                    taskRegistrar.setScheduler(executor);
                    taskRegistrar.afterPropertiesSet();
                    return null;
                }
                CronTrigger crontrigger = new CronTrigger(cronExpressions);
                return crontrigger.nextExecutionTime(triggerContext);
            }
        };
        taskRegistrar.addTriggerTask(runnableTask, trigger);
    }
}
