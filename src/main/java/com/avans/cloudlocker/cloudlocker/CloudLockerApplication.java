package com.avans.cloudlocker.cloudlocker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CloudLockerApplication {

    public static void main(String[] args) {
       SpringApplication application = new SpringApplication(CloudLockerApplication.class);
       application.run(args);
    }

}
