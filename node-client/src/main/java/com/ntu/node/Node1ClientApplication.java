package com.ntu.node;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = {"com.ntu.node","com.ntu.common"})
@MapperScan(basePackages = "com.ntu.node.mapper")
public class Node1ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(Node1ClientApplication.class, args);
    }

}
