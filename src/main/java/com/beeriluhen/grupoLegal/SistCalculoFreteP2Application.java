package com.beeriluhen.grupoLegal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.beeriluhen" })
@EntityScan(basePackages = { "com.beeriluhen" })
public class SistCalculoFreteP2Application {
	public static void main(String[] args) {
		SpringApplication.run(SistCalculoFreteP2Application.class, args);
	}
}
