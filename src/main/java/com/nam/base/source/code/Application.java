package com.nam.base.source.code;

import com.nam.base.source.code.utils.DotenvLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableCaching
public class Application {

    public static void main(String[] args) {
        DotenvLoader.loadEnv();
        SpringApplication.run(Application.class, args);
    }

}
