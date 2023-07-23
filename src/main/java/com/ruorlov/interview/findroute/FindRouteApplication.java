package com.ruorlov.interview.findroute;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class FindRouteApplication {

	public static void main(String[] args) {
		SpringApplication.run(FindRouteApplication.class, args);
	}

}
