package com.portfolio.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.portfolio")
public class PortfolioAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortfolioAppApplication.class, args);
	}

}
