package com.fatec.livrariaonlinejpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@AutoConfiguration
@EnableScheduling
public class LivrariaonlinejpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LivrariaonlinejpaApplication.class, args);
	}

}
