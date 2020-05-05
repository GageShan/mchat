package com.gageshan.mchat;

import com.gageshan.mchat.utils.SpringUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@MapperScan(basePackages = "com.gageshan.mchat.mapper")
@ComponentScan(basePackages = {"com.gageshan.mchat","org.n3r.idworker"})
public class MchatApplication {

    @Bean
    public SpringUtil getSpringUtil() {
        return new SpringUtil();
    }
    public static void main(String[] args) {
        SpringApplication.run(MchatApplication.class, args);
    }

}
