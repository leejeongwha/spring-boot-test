package com.naver.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringBootTestApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootTestApplication.class, args);
	}
}
