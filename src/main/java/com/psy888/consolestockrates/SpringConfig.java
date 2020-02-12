package com.psy888.consolestockrates;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.*;

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
        ThreadPoolTaskExecutor tpte =  new ThreadPoolTaskExecutor();
//        tpte.setCorePoolSize(25);
//        tpte.setMaxPoolSize(100);
//        tpte.setAllowCoreThreadTimeOut(true);
        return tpte;
    }

    @Bean(name = "cachedExecutor")
    public ExecutorService executor(){
        return Executors.newCachedThreadPool();
    }

}
