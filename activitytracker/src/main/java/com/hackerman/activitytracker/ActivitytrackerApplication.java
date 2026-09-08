package com.hackerman.activitytracker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
import org.springframework.boot.security.autoconfigure.actuate.web.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude={
		DataSourceAutoConfiguration.class})
public class ActivitytrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActivitytrackerApplication.class, args);
	}

	@Bean
	CommandLineRunner clr(){
		return  x -> {
			System.out.println("Hello From Raman!");
		};
	}

}
