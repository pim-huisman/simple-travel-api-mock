package com.klm.exercises.spring;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class Bootstrap {

    public static void main(final String[] args) throws IOException {
        SpringApplication.run(Bootstrap.class, args);
    }

}
