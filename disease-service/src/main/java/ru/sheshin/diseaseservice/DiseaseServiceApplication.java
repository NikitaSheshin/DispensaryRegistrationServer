package ru.sheshin.diseaseservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DiseaseServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiseaseServiceApplication.class, args);
    }

}
