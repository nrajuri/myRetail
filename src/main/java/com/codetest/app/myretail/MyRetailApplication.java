package com.codetest.app.myretail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Configuration;

/**
 * MyRetailApplication
 */
@SpringBootApplication
@EnableCircuitBreaker
@EnableHystrixDashboard
public class MyRetailApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyRetailApplication.class, args);
    }
}
