package com.bussinessmanagement.managementSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.ActiveProfiles;


@SpringBootApplication
@ActiveProfiles("test")
@EnableCaching
public class ManagementSystemApplication extends SpringBootServletInitializer{

	 @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ManagementSystemApplication.class);
    }
	public static void main(String[] args) {
		SpringApplication.run(ManagementSystemApplication.class, args);
	}

}
