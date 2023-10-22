package com.eat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@ComponentScans(value = {@ComponentScan("com.eat.untils"), @ComponentScan("com.eat.config")})
public class UCApp {
    public static void main(String[] args) {
        SpringApplication.run(UCApp.class, args);
    }
}
