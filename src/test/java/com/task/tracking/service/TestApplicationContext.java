package com.task.tracking.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"com.task.tracking.service.domain.repository"})
@EntityScan("com.task.tracking.service.domain.entity")
@ComponentScan(basePackages = {"com.task.tracking.service"})
public class TestApplicationContext {

    public static void main(String[] args) {
        SpringApplication.run(TestApplicationContext.class, args);
    }
}
