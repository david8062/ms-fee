package com.IusCloud.msFees;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class MsFeesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsFeesApplication.class, args);
    }
}
