package com.app.leavemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LeavemanagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeavemanagerApplication.class, args);
    }

}