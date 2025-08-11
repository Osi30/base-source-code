package com.nam.base.source.code;

import com.nam.base.source.code.utils.DotenvLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        DotenvLoader.loadEnv();
        SpringApplication.run(Application.class, args);
    }

}
