package com.wangjunblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(value = "com.wangjunblog.dao.mapper")
public class WangjunBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(WangjunBlogApplication.class, args);
    }

}
