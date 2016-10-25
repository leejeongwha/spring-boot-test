package com.naver.test;

import java.util.Random;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import com.naver.test.entity.Person;
import com.naver.test.repository.PersonRepository;

@SpringBootApplication
public class SpringBootTestApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootTestApplication.class, args);
	}
}

@Component
class SampleDataCLR implements CommandLineRunner {
	@Autowired
	private PersonRepository personRepository;

	@Override
	public void run(String... arg0) throws Exception {
		// 초기 데이터 생성
		Stream.of("Stephane Maldini", "Arjen Poutsma", "Rossen Stoyanchev", "Sebastien Deleuze", "Josh Long")
				.forEach(name -> personRepository.save(new Person(name, new Random().nextInt(100))));

		personRepository.findAll().forEach(System.out::println);
	}
}