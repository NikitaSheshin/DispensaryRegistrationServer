package ru.sheshin.dispensaryserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DispensaryServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DispensaryServerApplication.class, args);
    }

}
