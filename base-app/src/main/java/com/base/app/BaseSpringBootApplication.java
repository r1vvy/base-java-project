package com.base.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.base")
public class BaseSpringBootApplication {
    static void main() {
        SpringApplication.run(BaseSpringBootApplication.class);
    }
}