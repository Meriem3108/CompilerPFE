package com.example.testpfe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.testpfe.Repository")
@EntityScan(basePackages = "com.example.testpfe.Entity")
public class TestpfeApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestpfeApplication.class, args);
    }
}