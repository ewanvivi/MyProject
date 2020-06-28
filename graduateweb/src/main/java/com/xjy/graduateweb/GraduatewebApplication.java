package com.xjy.graduateweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import javax.sql.DataSource;

@MapperScan(basePackages = "com.xjy.graduateweb.mapper")
@SpringBootApplication()
public class GraduatewebApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraduatewebApplication.class, args);
    }

}
