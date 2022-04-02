package com.liner.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: Administrator
 * @date: 2022/3/27 11:17
 * @description:
 */

@SpringBootApplication
@ComponentScan(basePackages = {"com.liner"})
public class EduApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }

}
