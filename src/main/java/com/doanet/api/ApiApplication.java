package com.doanet.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@OpenAPIDefinition(
	info = @Info(
		title = "Doanet API",
		version = "MVP",
		description = "API desenvolvida para versão inicial do banco de dados de alimentos"
	)
)
public class ApiApplication {

	 public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);

	}
 }
