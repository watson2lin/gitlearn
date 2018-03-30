package com.youcash.uc;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.ws.rs.core.Application;

/**
 * Created by huanglin on 2017/3/30.
 */
@SpringBootApplication
public class ConsumerApp extends Application {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApp.class, args);
    }
}
