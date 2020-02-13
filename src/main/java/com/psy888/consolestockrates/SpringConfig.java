package com.psy888.consolestockrates;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Configuration
@ComponentScan
public class SpringConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
//
    @Bean(name = "taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        return new ThreadPoolTaskExecutor();
    }

    @Bean(name = "cachedExecutor")
    public ExecutorService executor(){
        return Executors.newCachedThreadPool();
    }

    @Bean
    ScheduledExecutorService scheduledExecutorService(){
        return new ScheduledThreadPoolExecutor(1);
    }

}
