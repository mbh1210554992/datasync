package com.ntu.datasync;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan(basePackages = "com.ntu.datasync.mapper")
public class DatasyncApplication {
	public static void main(String[] args) {
		SpringApplication.run(DatasyncApplication.class, args);
	}

}
