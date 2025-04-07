package com.example.IntegrationService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class ThreadPoolConfig {

    @Bean(name = "integrationTaskExecutor") //bean is made when object is required at runtime
    public Executor integrationTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);       // min 5 threads
        executor.setMaxPoolSize(10);       // max threads
        executor.setQueueCapacity(90);    // task queue size
        executor.setThreadNamePrefix("IntegrationThread-");
        executor.initialize();
        return executor;
    }
}
