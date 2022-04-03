package com.shuai.weather;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
@MapperScan("com.shuai.weather.mapper")
public class WeatherApplication {
    public static void main(String[] args) {
        SpringApplication.run(WeatherApplication.class,args);
    }
}
