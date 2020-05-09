package com.ntu.client2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = {"com.ntu.client2","com.ntu.common"})
@MapperScan(basePackages = "com.ntu.client2.mapper")
public class Node2ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(Node2ClientApplication.class, args);
    }

}
