package com.eserver.lab3server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class Lab3serverApplication {

    public static void main(String[] args) {
        SpringApplication.run(Lab3serverApplication.class, args);
    }

}
