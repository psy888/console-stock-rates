package com.psy888.consolestockrates.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedTransferQueue;

@Component
@EnableAsync
@EnableScheduling
public class RateRequestQueue {
    private Queue<Runnable> queue = new LinkedTransferQueue<>();
    @Autowired
    private Executor taskExecutor;

    public void addToQueue(Runnable task) {
        queue.add(task);
    }

    @Async
    public void updateAll() {
        int cnt = 0;
        while (!queue.isEmpty()) {
            taskExecutor.execute(queue.poll() );

            System.out.println(cnt++);
        }


    }

}
