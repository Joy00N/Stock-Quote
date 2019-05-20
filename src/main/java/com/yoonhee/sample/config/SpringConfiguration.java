package com.yoonhee.sample.config;

import com.yoonhee.sample.service.ScheduledTask;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableScheduling
public class SpringConfiguration{
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
//    @Bean
//    public TaskScheduler poolScheduler() {
//        return new ScheduledTask();
//    }
    @Bean
    public ScheduledTask task() {
        return new ScheduledTask();
    }

//    @Bean(destroyMethod = "shutdown")
//    public boolean taskExecutor() throws InterruptedException {
//        return Executors.newSingleThreadScheduledExecutor().awaitTermination(1, TimeUnit.MINUTES);
//    }

    @Bean
    public ThreadPoolTaskScheduler setSchedulerToWait(ThreadPoolTaskScheduler threadPoolTaskScheduler){
        threadPoolTaskScheduler.setAwaitTerminationSeconds(10000);
        return threadPoolTaskScheduler;
    }
}
