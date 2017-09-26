package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;

//corre la aplicacion (clase ejecutable)

@SpringBootApplication
@IntegrationComponentScan
@EnableIntegration
public class SbSqlServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbSqlServerApplication.class, args);
	}
}
