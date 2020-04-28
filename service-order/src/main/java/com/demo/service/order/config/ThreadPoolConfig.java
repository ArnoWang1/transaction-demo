package com.demo.service.order.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
@Slf4j
public class ThreadPoolConfig {

    @Bean
    public Executor executor() {
        log.info("init thread pool");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(10);
        //配置最大线程数
        executor.setMaxPoolSize(20);
        //配置队列大小
        executor.setQueueCapacity(10);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("service-order-tp-");
        //执行初始化
        executor.initialize();
        log.info("thread pool complete");
        return executor;
    }

}