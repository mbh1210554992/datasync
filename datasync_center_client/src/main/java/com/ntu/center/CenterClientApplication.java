package com.ntu.center;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = {"com.ntu.common","com.ntu.center"})
@MapperScan(basePackages = "com.ntu.center.mapper")
public class CenterClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(CenterClientApplication.class, args);
    }

}
