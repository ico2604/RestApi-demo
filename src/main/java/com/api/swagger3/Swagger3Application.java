package com.api.swagger3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Swagger3Application {

	public static void main(String[] args) {
		SpringApplication.run(Swagger3Application.class, args);
	}

	
}
