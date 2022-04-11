package com.sys.foodcommendbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.sys.foodcommendbackend.mapper")
@SpringBootApplication
public class FoodcommendbackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodcommendbackendApplication.class, args);
    }

}
