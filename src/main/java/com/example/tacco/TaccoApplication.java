package com.example.tacco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class TaccoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaccoApplication.class, args);
    }

}
